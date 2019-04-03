package org.isel.mpd.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {
    /**
     * V1 -- Iterator based
     */
    public static <T> Stream<T> collapse(Stream<T> src) {
        Iterable<T> srcIter = () -> src.iterator();
        Iterable<T> res = () -> new IteratorCollapse(srcIter);
        return StreamSupport.stream(res.spliterator(), false);
    }

    /**
     * V2 -- Spliterator based. Less Overhead:
     * 1. Avoids additional lambda wrapper: res = () -> ...
     * 2. Avoids further conversion: res.spliterator()...
     */
    public static <T> Stream<T> collapse2(Stream<T> src) {
        Iterable<T> srcIter = () -> src.iterator();
        SpliteratorCollapse<T> res = new SpliteratorCollapse<>(srcIter);
        return StreamSupport.stream(res, false);
    }
}
