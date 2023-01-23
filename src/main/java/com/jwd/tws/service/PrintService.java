package com.jwd.tws.service;

import com.jwd.tws.cache.TopNCache;

/**
 * Print service used to display the results to the user
 */
public interface PrintService {
    /**
     * Prints top 'n' cached results
     *
     * @param topNCache that holds the top 'n' word sequence along with it's count.
     */
    void printTopNCache(TopNCache topNCache);
}
