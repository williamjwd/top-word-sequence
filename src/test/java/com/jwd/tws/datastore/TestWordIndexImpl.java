package com.jwd.tws.datastore;

import com.jwd.tws.cache.TopNCacheImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWordIndexImpl {

    @Test
    public void testWithSameDepth() {
        WordIndexImpl wordIndex = new WordIndexImpl(3, 3, new TopNCacheImpl(5));
        String[] values = {"John", "Doe", "is", "sometimes", "used", "to", "refer", "to", "a", "typical", "male", "in", "other", "contexts", "as", "well", "John", "Doe", "is", "used", "to", "refer", "John", "Doe", "is"};
        for (int i = 0; i < 1; i++) {
            for (String val : values) {
                wordIndex.addWord(val);
            }
        }
        Stream<Map.Entry<String, Long>> entryStream = wordIndex.getTopNCache().getPrintableCache();
        List<Map.Entry<String, Long>> entryList = entryStream.toList();
        assertEquals(5, entryList.size());
        assertEquals("John Doe is", entryList.get(0).getKey());
        assertEquals(3, entryList.get(0).getValue());
        assertEquals("used to refer", entryList.get(1).getKey());
        assertEquals(2, entryList.get(1).getValue());
    }

    @Test
    public void testWithDifferentDepth() {
        WordIndexImpl wordIndex = new WordIndexImpl(3, 2, new TopNCacheImpl(5));
        String[] values = {"John", "Doe", "is", "sometimes", "used", "to", "refer", "to", "a", "typical", "male", "in", "other", "contexts", "as", "well", "John", "Doe", "used", "to", "John", "Doe"};
        for (int i = 0; i < 1; i++) {
            for (String val : values) {
                wordIndex.addWord(val);
            }
        }
        Stream<Map.Entry<String, Long>> entryStream = wordIndex.getTopNCache().getPrintableCache();
        List<Map.Entry<String, Long>> entryList = entryStream.toList();
        assertEquals(5, entryList.size());
        assertEquals("John Doe", entryList.get(0).getKey());
        assertEquals(3, entryList.get(0).getValue());
        assertEquals("used to", entryList.get(1).getKey());
        assertEquals(2, entryList.get(1).getValue());
    }
}
