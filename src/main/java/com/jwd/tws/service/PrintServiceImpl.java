package com.jwd.tws.service;

import com.jwd.tws.cache.TopNCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Implementation for the print service.  It is used to print the top 'n' cache data to the user as info logs.
 */
@Service
public class PrintServiceImpl implements PrintService {
    private final Logger logger = LoggerFactory.getLogger(PrintServiceImpl.class);

    // Separator between the word sequence and the count.  It is set to - by default.  The value can be changed in the
    // application.properties or overridden by passing it as the input parameter.
    private final String separator;
    public PrintServiceImpl(@Value( "${tws.print.separator:=>}") String separator) {
        this.separator = separator;
    }

    /**
     * Prints top 'n' cache as info logs.
     *
     * @param topNCache that holds the top 'n' word sequence along with it's count.
     */
    @Override
    public void printTopNCache(TopNCache topNCache) {
        topNCache.getPrintableCache()
                .forEach(this::printEntry);
    }

    private void printEntry(Map.Entry<String, Long> entry) {
        logger.info("{} {} {}", entry.getKey(), separator, entry.getValue());
    }
}
