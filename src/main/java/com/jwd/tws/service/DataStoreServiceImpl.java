package com.jwd.tws.service;

import com.jwd.tws.datastore.WordIndex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Data store service implementation.  It uses the application properties to pass the storage depth, cache depth,
 * and cache size to the subsequent storage classes.
 */
@Service
public class DataStoreServiceImpl implements DataStoreService {

    private final WordIndex wordIndex;

    public DataStoreServiceImpl(
            @Value( "${tws.storage.depth:3}") Integer storageDepth,
            @Value( "${tws.cache.depth:3}") Integer cacheDepth,
            @Value( "${tws.cache.size:100}") Integer cacheSize,
            BeanFactory beanFactory) {
        wordIndex = beanFactory.getBean(WordIndex.class, storageDepth, cacheDepth, cacheSize);
    }

    /**
     * If the word is not blank, it gets stored to the word index and the cache.
     *
     * @param word to be stored
     */
    @Override
    public void storeWord(String word) {
        if (StringUtils.isNotBlank(word)) {
            wordIndex.addWord(word.toLowerCase());
        }
    }

    /**
     * Returns the word index implementation.
     *
     * @return the word index implementation
     */
    @Override
    public WordIndex getWordIndex() {
        return wordIndex;
    }
}
