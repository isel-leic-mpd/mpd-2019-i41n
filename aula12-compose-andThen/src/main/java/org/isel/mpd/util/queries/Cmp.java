package org.isel.mpd.util.queries;

import java.util.Comparator;
import java.util.function.Function;

public class Cmp {
    public static <T, R extends Comparable<R>> Cmposable<T> comparing(Function<T, R> getter) {
        return new Cmposable<>() {
            @Override
            public int compare(T o1, T o2) {
                return getter.apply(o1).compareTo(getter.apply(o2));
            }
        };
    }
}
