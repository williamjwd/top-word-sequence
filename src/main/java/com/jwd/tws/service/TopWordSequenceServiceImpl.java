package com.jwd.tws.service;

import com.jwd.tws.util.InputReadStrategy;
import com.jwd.tws.util.ReadFromFile;
import com.jwd.tws.util.ReadFromStdIn;
import com.jwd.tws.util.WordTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for the top word sequence service.  It processes the input and builds the index and the top 'n' cache.
 */
@Service
public class TopWordSequenceServiceImpl implements TopWordSequenceService {
    private final DataStoreService dataStoreService;
    private final Logger logger = LoggerFactory.getLogger(TopWordSequenceServiceImpl.class);

    private final BeanFactory beanFactory;
    @Autowired
    public TopWordSequenceServiceImpl(BeanFactory beanFactory, DataStoreService dataStoreService) {
        this.beanFactory = beanFactory;
        this.dataStoreService = dataStoreService;
    }

    /**
     * Process the input as program arguments or input via stdin.  File path can be provided in the
     * program argument or the content can be streamed as input via stdin.
     *
     * @param args list of file paths
     */
    @Override
    public void processInput(String... args) {
        if (args == null || args.length == 0) {
            readAndStore(beanFactory.getBean(ReadFromStdIn.class));
        } else {
            for (String arg : args) {
                readAndStore(beanFactory.getBean(ReadFromFile.class, arg));
            }
        }
    }

    private void readAndStore(InputReadStrategy inputReadStrategy) {
        try {
            WordTokenizer<String> wordTokenizer = beanFactory.getBean(WordTokenizer.class, inputReadStrategy);

            while (wordTokenizer.hasMoreElements()) {
                dataStoreService.storeWord(wordTokenizer.nextElement());
            }
        } catch (BeansException e) {
            logger.error("Error occurred while processing input: ", e);
        }
    }
}
