package com.jwd.tws.datastore;

import com.jwd.tws.cache.TopNCache;

/**
 * Index maintained for the word to quickly identify the top 'n' count.  The word index can also be used in other
 * use cases like building a word cloud image or type-ahead word search.  Index can be maintained at higher depth
 * than the top 'n' cache.
 *
 * {@link WordIndexImpl} uses an in-memory data store.  Use this interface to provide your own implementation to
 * replace the in-memory data store to any data store of your choice.
 */
public interface WordIndex {
    /**
     * Add word to the index.  Depending on the depth maintained for the word index, the word gets inserted or
     * the count gets incremented at multiple levels.
     *
     * @param word to be added to the index.
     */
    void addWord(String word);

    /**
     * Returns the top 'n' cache maintained in the data store.
     *
     * @return top 'n' cache
     */
    TopNCache getTopNCache();
}
