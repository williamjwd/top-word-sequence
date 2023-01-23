package com.jwd.tws.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Iterator;

/**
 * Implementation to tokenize the words read from file or StdIn.  It uses the provided strategy to read the file or
 * StdIn and the strategy to split words from each and every line.
 */
public class WordTokenizerImpl implements WordTokenizer<String> {
    private String[] words;
    private final Iterator<String> lines;
    private Integer curIndex = 0;
    private Integer size = 0;
    private final TokenSplitStrategy<String> tokenSplitStrategy;

    public WordTokenizerImpl(InputReadStrategy inputReadStrategy, TokenSplitStrategy<String> tokenSplitStrategy) throws IOException {
        this.tokenSplitStrategy = tokenSplitStrategy;
        lines = inputReadStrategy.readLines();
        nextLine();
    }

    private void nextLine() {
        if (lines.hasNext()) {
            words = tokenSplitStrategy.split(lines.next());
            size = words.length;
        } else {
            words = null;
            size = 0;
        }
        curIndex = 0;
    }

    /**
     * It should reach end of line and end of word in the last like to determine that there are no more elements to read.
     *
     * @return true if there are more elements to read, false otherwise.
     */
    @Override
    public boolean hasMoreElements() {
        return lines.hasNext() || size > curIndex;
    }

    /**
     * Returns the next element/word.  If it reaches the last word in a line, then it moves to next like that isn't
     * blank.  If it reaches the last line and last word, it returns null.
     *
     * @return
     */
    @Override
    public String nextElement() {
        skipEmptyLines();

        if (words != null) {
            skipEmptyString();

            if (size > curIndex) {
                return words[curIndex++];
            } else {
                return nextElement();
            }
        }

        return null;
    }

    private void skipEmptyLines() {
        while (curIndex >= size && words != null) {
            nextLine();
        }
    }

    private void skipEmptyString() {
        while (size > curIndex && StringUtils.isBlank(words[curIndex])) {
            curIndex++;
        }
    }
}
