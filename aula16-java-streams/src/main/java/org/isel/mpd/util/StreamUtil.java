package org.isel.mpd.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtil {
    public static <T> Stream<T> collapse(Stream<T> src) {
        Iterable<T> res = () -> new IteratorCollapse(() -> src.iterator());
        return StreamSupport.stream(res.spliterator(), false);
    }
}
