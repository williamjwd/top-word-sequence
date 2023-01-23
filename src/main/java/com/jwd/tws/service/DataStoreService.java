package com.jwd.tws.service;

import com.jwd.tws.datastore.WordIndex;

/**
 * Service layer to handle the data storage.
 */
public interface DataStoreService {
    /**
     * Stores the word to the data store.  Data store includes both the index storage and the top 'n' cache.
     *
     * @param word to be stored
     */
    void storeWord(String word);

    /**
     * Returns the word index implementation.
     *
     * @return word index implementation
     */
    WordIndex getWordIndex();
}
