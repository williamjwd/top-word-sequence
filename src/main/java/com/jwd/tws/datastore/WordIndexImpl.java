package com.jwd.tws.datastore;

import com.jwd.tws.cache.TopNCache;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the word index data store.
 */
public class WordIndexImpl implements WordIndex {
    public static final String WORD_SEPARATOR = " ";

    // Map maintained for the root nodes
    private final Map<String, Node> nodeMap;

    // List of previously added nodes.  It is used to add new word to all the previously added nodes based on the depth.
    private List<Node> oldNodes;

    // Word index depth.  It is set to 3 by default.  The value can be changed in the application.properties
    // or overridden by passing it as the input parameter.
    private final Integer maxDepth;

    // Cache depth to maintain.  It is set to 3 by default.  The value can be changed in the application.properties
    // or overridden by passing it as the input parameter.
    private final Integer cacheDepth;

    // Top 'n' cache that gets added/updated while the word gets stored to the word index data store.
    private final TopNCache topNCache;

    public WordIndexImpl(Integer maxDepth, Integer cacheDepth, TopNCache topNCache) {
        // Gracefully handle if maxDepth is less than cacheDepth, and the range for maxDepth and cacheDepth instead
        // of throwing error
        if (maxDepth < cacheDepth) {
            this.maxDepth = Math.min(Math.max(cacheDepth, 1), 10);
        } else {
            this.maxDepth = Math.min(Math.max(maxDepth, 1), 10);
        }
        this.cacheDepth = Math.min(Math.max(cacheDepth, 1), 10);
        this.nodeMap = new HashMap<>();
        this.oldNodes = new ArrayList<>();
        this.topNCache = topNCache;
    }

    /**
     * Adds word to the word index.  The top 'n' cache gets updated after the node gets added to the index.
     *
     * @param word to be added to the index.
     */
    @Override
    public void addWord(String word) {
        Node node = createNodeOrIncrementCount(word);

        nodeMap.put(word, node);

        List<Node> newNodes = populateNewNodes(word);

        rebuildOldNodesFromNewNodes(newNodes);

        oldNodes.add(node);

        populateCache();
    }

    private void populateCache() {
        int cIndex = maxDepth - cacheDepth;

        if (cIndex < oldNodes.size() && oldNodes.get(cIndex).getLevel().equals(cacheDepth)) {
            Node cNode = oldNodes.get(cIndex);
            topNCache.addOrUpdate(generateKey(cNode), cNode.getCount());
        }
    }

    private void rebuildOldNodesFromNewNodes(List<Node> newNodes) {
        oldNodes.clear();
        oldNodes = new ArrayList<>(newNodes);
    }

    private List<Node> populateNewNodes(String word) {
        List<Node> newNodes = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(oldNodes)) {
            removeFirstNode(oldNodes);

            addWordToNewNodes(word, newNodes);
        }

        return newNodes;
    }

    private void addWordToNewNodes(String word, List<Node> newNodes) {
        for (Node n : oldNodes) {
            newNodes.add(n.addChild(word));
        }
    }

    private void removeFirstNode(List<Node> nodes) {
        if (nodes.size() == maxDepth) {
            nodes.remove(0);
        }
    }

    private Node createNodeOrIncrementCount(String word) {
        Node node = nodeMap.get(word);

        if (node == null) {
            node = new Node(word);
        } else {
            node.incrementCount();
        }
        return node;
    }

    // Appends words space separated to form a key
    private String generateKey(Node node) {
        Node tempNode = node;
        StringBuilder sb = new StringBuilder(tempNode.getWord());
        while (tempNode.getParent() != null) {
            tempNode = tempNode.getParent();
            sb.insert(0, WORD_SEPARATOR);
            sb.insert(0, tempNode.getWord());
        }
        return sb.toString();
    }

    /**
     * Returns the top 'n' cache implementation
     *
     * @return top 'n' cache
     */
    @Override
    public TopNCache getTopNCache() {
        return topNCache;
    }

}
