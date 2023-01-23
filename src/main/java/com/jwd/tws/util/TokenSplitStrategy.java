package com.jwd.tws.util;

/**
 * Strategy interface to split the token and return a string array
 * @param <T> token type
 */
public interface TokenSplitStrategy<T> {
    /**
     * Returns an array of String after performing the split
     *
     * @param value to be split
     * @return array of split String
     */
    String[] split(T value);
}
