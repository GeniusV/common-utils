package com.geniusver.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GeniusV
 */
class MessageFormatterTest {

    @Test
    void testFormat() {
        String result = MessageFormatter.format("message: {}, {}", 1, 2);
        assertEquals("message: 1, 2", result);
    }
}