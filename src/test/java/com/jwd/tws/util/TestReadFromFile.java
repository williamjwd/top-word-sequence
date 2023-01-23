package com.jwd.tws.util;

import com.jwd.tws.util.ReadFromFile;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestReadFromFile {
    @Test
    public void testReadLines() throws IOException {
        ReadFromFile readFromFile = new ReadFromFile(new FileInputStream(this.getClass().getResource("/example.txt").getFile()));
        Iterator<String> iterator = readFromFile.readLines();
        assertNotNull(iterator);
        int lineCtr = 0;
        while (iterator.hasNext()) {
            iterator.next();
            lineCtr++;
        }
        assertEquals(21, lineCtr);
    }

    @Test
    public void testInvalidFile() throws IOException {
        assertThrows(FileNotFoundException.class, () -> {
            new ReadFromFile("/invalid_file.txt");
        });
    }
}
