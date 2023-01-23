package com.jwd.tws.cache;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTopNCacheImpl {
    @Test
    public void testCache() {
        TopNCacheImpl topNCache = new TopNCacheImpl(5);
        topNCache.addOrUpdate("this", 1L);
        topNCache.addOrUpdate("that", 10L);
        topNCache.addOrUpdate("this", 20L);
        topNCache.addOrUpdate("where", 1L);
        topNCache.addOrUpdate("there", 1L);
        topNCache.addOrUpdate("when", 1L);
        topNCache.addOrUpdate("next", 1L);
        topNCache.addOrUpdate("year", 5L);
        topNCache.addOrUpdate("test", 3L);
        topNCache.addOrUpdate("done", 50L);

        List<Map.Entry<String, Long>> entryList = topNCache.getPrintableCache().toList();

        assertEquals(5, entryList.size());
        assertEquals("done", entryList.get(0).getKey());
        assertEquals(50, entryList.get(0).getValue());
        assertEquals("this", entryList.get(1).getKey());
        assertEquals(20, entryList.get(1).getValue());
        assertEquals("test", entryList.get(4).getKey());
        assertEquals(3, entryList.get(4).getValue());
    }
}
