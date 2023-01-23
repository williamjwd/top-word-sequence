package com.jwd.tws;

import com.jwd.tws.service.DataStoreService;
import com.jwd.tws.service.PrintService;
import com.jwd.tws.service.TopWordSequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Command line runner for the top word sequence feature
 */
@Component
public class TopWordSequenceCommandLineRunner implements CommandLineRunner {

    private final TopWordSequenceService twsService;
    private final DataStoreService dataStoreService;
    private final PrintService printService;

    @Autowired
    public TopWordSequenceCommandLineRunner(TopWordSequenceService twsService, DataStoreService dataStoreService, PrintService printService) {
        this.twsService = twsService;
        this.dataStoreService = dataStoreService;
        this.printService = printService;
    }
    private final Logger logger = LoggerFactory.getLogger(TopWordSequenceCommandLineRunner.class);
    @Override
    public void run(String...args) {
        logger.info("Application Started !!");
        long processStart = System.nanoTime();
        twsService.processInput(args);
        long processEnd = System.nanoTime();
        printService.printTopNCache(dataStoreService.getWordIndex().getTopNCache());
        logger.info("Time taken to process: {} ms", (processEnd - processStart) / 1_000_000);
        logger.info("Time taken to print: {} ms", (System.nanoTime() - processEnd) / 1_000_000);
    }
}