package com.jwd.tws.service;

/**
 * Main service that processes the input either as program arguments or input via stdin
 */
public interface TopWordSequenceService {
    /**
     * Method to process the input as program arguments or input via stdin.  File path can be provided in the
     * program argument or the content can be streamed as input via stdin.
     *
     * @param args list of file paths
     */
    void processInput(String... args);
}
