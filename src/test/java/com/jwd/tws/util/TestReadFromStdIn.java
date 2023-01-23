package com.jwd.tws.util;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestReadFromStdIn {
    @Test
    public void testReadLines() throws IOException, URISyntaxException {
        List<String> listOfStr = Files.lines(Paths.get(this.getClass().getResource("/example.txt").toURI())).toList();
        ByteArrayInputStream bais = new ByteArrayInputStream(getByteArray(listOfStr));
        System.setIn(bais);

        ReadFromStdIn readFromStdIn = new ReadFromStdIn();
        Iterator<String> iterator = readFromStdIn.readLines();
        assertNotNull(iterator);
        int lineCtr = 0;
        while (iterator.hasNext()) {
            iterator.next();
            lineCtr++;
        }
        assertEquals(21, lineCtr);

    }

    private byte[] getByteArray(List<String> listOfStr) throws IOException {
        // write to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        for (String element : listOfStr) {
            out.writeBytes(element);
            out.writeBytes("\n");
        }

        return baos.toByteArray();
    }
}
