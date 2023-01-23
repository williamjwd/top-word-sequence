package com.jwd.tws.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Implementation to read from StdIn
 */
public class ReadFromStdIn implements InputReadStrategy {
    /**
     * Returns a String iterator for all the lines read from StdIn.
     *
     * @return a String iterator
     * @throws IOException
     */
    @Override
    public Iterator<String> readLines() throws IOException {
        InputStreamReader isReader = new InputStreamReader(System.in);
        try (BufferedReader br = new BufferedReader(isReader)) {
            if (br.ready()) {
                return br.lines().toList().iterator();
            }
            return null;
        }
    }
}
