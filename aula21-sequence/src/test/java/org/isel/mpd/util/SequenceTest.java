package org.isel.mpd.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SequenceTest {
    @Test
    public void testSequenceMap() {
        Sequence<String> words = Sequence.of("abc", "isel", "super", "mania");
        Iterator<Integer> expected = Arrays.asList(3, 4, 5, 5).iterator();
        words
            .map(String::length)
            .forEach(l -> assertEquals(expected.next(), l));
        assertFalse(expected.hasNext());
    }

}
