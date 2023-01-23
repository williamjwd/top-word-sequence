package com.jwd.tws.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Simple word splitter implementation to split lines into multiple words.
 */
@Component
public class SimpleWordSplitter implements TokenSplitStrategy<String> {

    // Word split regex pattern.  (\s|[.]|[,]|[:]|[?]|[@]|[//]|[(]|[)]|["]|[?]|[?]|[-]|[+]|[#]|[?]|[=]|[&]|[!]|[;])+
    // is provided as default.  The value can be changed in the application.properties or overridden by passing it as
    // the input parameter.
    private final String wordSplitPattern;

    public SimpleWordSplitter(@Value( "${tws.word.pattern:(\\s|[.]|[,]|[:]|[?]|[@]|[//]|[(]|[)]|[”]|[\"]|[?]|[?]|[-]|[+]|[#]|[?]|[=]|[&]|[!]|[;])+}") String wordSplitPattern) {
        this.wordSplitPattern = wordSplitPattern;
    }

    /**
     * Returns an array of String after performing the split based on the provided pattern
     *
     * @param value to be split
     * @return array of split String
     */
    @Override
    public String[] split(String value) {
        return value.split(wordSplitPattern);
    }
}
