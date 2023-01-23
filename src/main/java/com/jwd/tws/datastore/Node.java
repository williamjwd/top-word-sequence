package com.jwd.tws.datastore;

import java.util.HashMap;
import java.util.Map;

/**
 * Node class to maintain the word, it's count, parent, level, and children in the data store.
 */
public class Node {
    private final Node parent;
    private final String word;
    private Long count;
    private final Integer level;
    private final Map<String, Node> children;

    private Node(String word, Long count, Integer level, Node parent) {
        this.word = word;
        this.count = count;
        this.level = level;
        this.parent = parent;
        this.children = new HashMap<>();
    }

    private Node(String word, Integer level, Node parent) {
        this(word, 1L, level, parent);
    }

    public Node(String word) {
        this(word, 1L, 1, null);
    }

    public Node addChild(String value) {
        Node node = createNodeOrIncrementCount(value);

        children.put(value, node);

        return node;
    }

    private Node createNodeOrIncrementCount(String value) {
        Node node = children.get(value);

        if (node == null) {
            node = new Node(value, level + 1, this);
        } else {
            node.incrementCount();
        }
        return node;
    }

    public String getWord() {
        return word;
    }

    public Long getCount() {
        return count;
    }

    public Integer getLevel() {
        return level;
    }
    public void incrementCount() {
        count++;
    }

    public Node getParent() {
        return parent;
    }
}
