package com.jwd.tws.cache;

import java.util.*;
import java.util.stream.Stream;

/**
 * In-memory implementation for the Top 'n' cache.
 */
public class TopNCacheImpl implements TopNCache {
    // Map to maintain the top 'n' word sequence, and it's count
    private final Map<String, Long> topNMap;

    // Set multimap to maintain the word sequence indexed by the count.  This is used as an index while managing
    // the order of the word sequence, and to eliminate the word sequence at the bottom 'n' list to replace it
    // with the higher priority word sequence.
    private final Map<Long, Set<String>> topNMultimap;

    // Cache size to maintain.  It is set to 100 by default.  The value can be changed in the application.properties
    // or overridden by passing it as the input parameter.
    private final Integer cacheSize;
    private Long minCount = 1L;
    private Long maxCount = 1L;
    private Integer size = 0;

    public TopNCacheImpl(Integer cacheSize) {
        this.cacheSize = cacheSize;
        this.topNMap = new HashMap<>();
        this.topNMultimap = new HashMap<>();
    }

    /**
     * Adds the key along with the count to the in-memory cache.  If the key already exists, old one is removed
     * before adding the key with the new count.
     *
     * @param key cache entry key
     * @param count number of elements present
     */
    @Override
    public void addOrUpdate(String key, Long count) {
        Long curCount = topNMap.get(key);
        if (curCount == null) {
            if (size < cacheSize) {
                minCount = Math.min(count, minCount);
                maxCount = Math.max(count, maxCount);
                size++;
                addToCache(key, count);
            } else if (count > minCount) {
                String removeKey = removeFirstFromMultimap(topNMultimap, minCount);
                setNextMinCount();
                removeFromTopNMap(removeKey);
                addToCache(key, count);
            }
        } else {
            removeFromMultimap(topNMultimap, curCount, key);
            setNextMinCount();
            removeFromTopNMap(key);
            addToCache(key, count);
        }
    }

    private void removeFromTopNMap(String removeKey) {
        topNMap.remove(removeKey);
    }

    private void addToCache(String key, Long count) {
        topNMap.put(key, count);
        addToMultimap(topNMultimap, count, key);
    }

    private void setNextMinCount() {
        while (topNMultimap.get(minCount) == null) {
            minCount++;
        }
    }

    private void addToMultimap(Map<Long, Set<String>> topNMultimap, Long count, String key) {
        Set<String> values = topNMultimap.get(count);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(key);
        topNMultimap.put(count, values);
    }

    private String removeFirstFromMultimap(Map<Long, Set<String>> topNMultimap, Long count) {
        Set<String> values = topNMultimap.get(count);
        Iterator<String> it = values.iterator();
        if (!it.hasNext()) {
            return null;
        }
        String removed = it.next();
        it.remove();
        if (values.isEmpty()) {
            topNMultimap.remove(count);
        }
        return removed;
    }

    private void removeFromMultimap(Map<Long, Set<String>> topNMultimap, Long count, String value) {
        Set<String> values = topNMultimap.get(count);
        values.remove(value);
        if (values.isEmpty()) {
            topNMultimap.remove(count);
        }
    }

    /**
     * Returns the printable cache entries from the in-memory cache.
     *
     * @return stream of {@link Map.Entry}
     */
    @Override
    public Stream<Map.Entry<String, Long>> getPrintableCache() {
        return topNMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
    }
}
