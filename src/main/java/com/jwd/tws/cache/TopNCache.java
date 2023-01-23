package com.jwd.tws.cache;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Cache to store top 'n' data points along with the count.  {@link TopNCacheImpl} uses an in-memory cache.
 * Use this interface to provide your own implementation to replace the in-memory cache to any data store
 * of your choice.
 */
public interface TopNCache {
    /**
     * Adds to the cache if the key doesn't exist already.  Update the cache if key already exists.
     *
     * @param key cache entry key
     * @param count number of elements present
     */
    void addOrUpdate(String key, Long count);

    /**
     * Returns a stream of {@link Map.Entry} to be displayed to the user.
     *
     * @return stream of {@link Map.Entry}
     */
    Stream<Map.Entry<String, Long>> getPrintableCache();
}
