package com.jwd.tws.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Implementation to read from file
 */
public class ReadFromFile implements InputReadStrategy {

    private final FileInputStream fileInputStream;

    public ReadFromFile(String filePath) throws FileNotFoundException {
        this(new FileInputStream(filePath));
    }

    public ReadFromFile(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    /**
     * Returns a String iterator for all the lines read from the file.
     *
     * @return a String iterator
     * @throws IOException
     */
    @Override
    public Iterator<String> readLines() throws IOException {
        InputStreamReader isReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(isReader)) {
            return br.lines().toList().iterator();
        }
    }
}
