package com.jwd.tws.config;

import com.jwd.tws.cache.TopNCache;
import com.jwd.tws.cache.TopNCacheImpl;
import com.jwd.tws.datastore.WordIndex;
import com.jwd.tws.datastore.WordIndexImpl;
import com.jwd.tws.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Spring configuration file for bean definition and instantiation
 */
@Configuration
public class TopWordSequenceAppConfig {

    @Autowired TokenSplitStrategy<String> tokenSplitStrategy;
    @Bean
    @Scope(value = "prototype")
    public WordTokenizer<String> wordTokenizer(InputReadStrategy inputReadStrategy) throws IOException {
        return new WordTokenizerImpl(inputReadStrategy, tokenSplitStrategy);
    }

    @Bean
    @Scope(value = "prototype")
    public ReadFromStdIn readFromStdIn() {
        return new ReadFromStdIn();
    }

    @Bean
    @Scope(value = "prototype")
    public ReadFromFile readFromFile(String filePath) throws FileNotFoundException {
        return new ReadFromFile(filePath);
    }

    @Bean
    @Scope(value = "prototype")
    public WordIndex wordIndex(Integer maxDepth, Integer cacheDepth, Integer cacheSize) {
        return new WordIndexImpl(maxDepth, cacheDepth, topNCache(cacheSize));
    }

    @Bean
    @Scope(value = "prototype")
    public TopNCache topNCache(Integer cacheSize) {
        return new TopNCacheImpl(cacheSize);
    }
}
