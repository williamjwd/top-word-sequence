package com.jwd.tws.util;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestWordTokenizerImpl {

    @Test
    public void testHasMoreElements() throws IOException {
        WordTokenizerImpl wordTokenizer = new WordTokenizerImpl(
                new ReadFromFile(new FileInputStream(this.getClass().getResource("/small.txt").getFile())),
                new SimpleWordSplitter("(\\s|[.]|[,]|[:])+"));
        assertTrue(wordTokenizer.hasMoreElements());
        int wordCtr = 0;
        while (wordTokenizer.hasMoreElements()) {
            wordCtr++;
            wordTokenizer.nextElement();
        }
        assertEquals(7, wordCtr);

    }

    @Test
    public void testNextElements() throws IOException {
        WordTokenizerImpl wordTokenizer = new WordTokenizerImpl(
                new ReadFromFile(new FileInputStream(this.getClass().getResource("/small.txt").getFile())),
                new SimpleWordSplitter("(\\s|[.]|[,]|[:])+"));
        String[] expected = {"sample", "text", "file", "to", "perform", "simple", "tests"};
        int wordCtr = 0;
        while (wordTokenizer.hasMoreElements()) {
            assertEquals(expected[wordCtr++], wordTokenizer.nextElement());
        }
    }
}
