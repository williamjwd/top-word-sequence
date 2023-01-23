package com.jwd.tws.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSimpleWordSplitter {
    @Test
    public void testSplit() {
        SimpleWordSplitter sws = new SimpleWordSplitter("(\\s|[.]|[,]|[:]|[?]|[@]|[//]|[(]|[)]|[”]|[“]|[-]|[+]|[*]|[#]|[?]|[=]|[&]|[!]|[;]|[\"])+");
        String[] expected = {"Purpose", "Provide", "example", "of", "this", "file", "type", "ahead"};
        String[] words = sws.split("Purpose: ,Provide? :example/ (of) this =*+file& type-“ahead”!");
        assertEquals(8, words.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], words[i]);
        }
    }

    @Test
    public void testSplitWithUnicode() {
        SimpleWordSplitter sws = new SimpleWordSplitter("(\\s|[.]|[,]|[:]|[?]|[@]|[//]|[(]|[)]|[”]|[“]|[-]|[+]|[*]|[#]|[?]|[=]|[&]|[!]|[;]|[\"])+");
        String[] expected = {"ღმერთსი", "შემვედრე", "ნუთუ", "კვლა", "დამხსნას", "სოფლისა", "შრომასა", "ცეცხლს", "წყალსა", "და", "მიწასა", "ჰაერთა", "თანა", "მრომასა", "მომცნეს", "ფრთენი", "და", "აღვფრინდე", "მივჰხვდე", "მას", "ჩემსა", "ნდომასა", "დღისით", "და", "ღამით", "ვჰხედვიდე", "მზისა", "ელვათა", "კრთომაასა"};
        String[] words = sws.split("ღმერთსი შემვედრე, ნუთუ კვლა დამხსნას სოფლისა შრომასა, ცეცხლს, წყალსა და მიწასა, ჰაერთა თანა მრომასა; მომცნეს ფრთენი და აღვფრინდე, მივჰხვდე მას ჩემსა ნდომასა, დღისით და ღამით ვჰხედვიდე მზისა ელვათა კრთომაასა.");
        assertEquals(29, words.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], words[i]);
        }

    }
}
