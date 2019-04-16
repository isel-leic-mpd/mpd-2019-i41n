package org.isel.mpd.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
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
    @Test
    public void testMaxViaReduce() {
        Random rand = new Random();
        Integer[] nrs = Stream
            .generate(() -> rand.nextInt(100))
            .limit(20)
            .toArray(size -> new Integer[size]);
        int expected = Stream
            .of(nrs)
            .max(Integer::compareTo)
            .get();
        int actual = Sequence
            .of(nrs)
            .reduce(nrs[0], (prev, curr) -> prev > curr ? prev : curr);
        assertEquals(expected, actual);
    }
    @Test
    public void testSumViaReduce() {
        Random rand = new Random();
        int[] nrs = IntStream
            .generate(() -> rand.nextInt(100))
            .limit(20)
            .toArray();

        int expected = IntStream
            .of(nrs)
            .sum();

        int sum = Sequence
            .of(nrs)
            .reduce(0, (prev, curr) -> prev + curr);
        assertEquals(expected, sum);
    }

    @Test
    public void testToListViaReduce() {
        Random rand = new Random();
        Integer[] nrs = Stream
            .generate(() -> rand.nextInt(100))
            .limit(20)
            .toArray(size -> new Integer[size]);

        List<Integer> lst = new ArrayList<>();
        List<Integer> actual = Sequence
            .of(nrs)
            .reduce(lst, (prev, curr) -> {
                prev.add(curr);
                return prev;
            });
        assertArrayEquals(nrs, actual.toArray());
    }

    @Test
    public void testToListViaReduceOnStream() {
        Random rand = new Random();
        Integer[] nrs = Stream
            .generate(() -> rand.nextInt(100))
            .limit(1024*8)
            .toArray(size -> new Integer[size]);

        List<Integer> lst = new LinkedList<>();
        List<Integer> actual = Stream
            .of(nrs)
            .parallel()
            .reduce(lst, (prev, curr) -> {
                prev.add(curr);
                return prev;
            }, (l1, l2) -> { l1.addAll(l2); return l1; });


    }

    @Test
    public void testToListViaCollectOnStream() {
        Random rand = new Random();
        Integer[] nrs = Stream
            .generate(() -> rand.nextInt(100))
            .limit(1024*8)
            .toArray(size -> new Integer[size]);

        List<Integer> actual = Stream
            .of(nrs)
            .parallel()
            .collect(
                LinkedList::new,
                LinkedList::add,
                LinkedList::addAll);


    }

}
