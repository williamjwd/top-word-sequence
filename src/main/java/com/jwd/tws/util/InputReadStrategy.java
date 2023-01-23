package com.jwd.tws.util;

import java.io.IOException;
import java.util.Iterator;

/**
 * Strategy interface to read input and convert it to {@link Iterator<String>}
 */
public interface InputReadStrategy {
    /**
     * Returns a String iterator for all the lines read.
     *
     * @return a String iterator
     * @throws IOException
     */
    Iterator<String> readLines() throws IOException;
}
