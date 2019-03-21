package org.isel.mpd.util.queries;

import java.util.Comparator;
import java.util.function.Function;

public class Cmp {
    public static <T, R extends Comparable<R>> MyCmp<T> comparing(Function<T, R> getter) {
        return new MyCmp<>() {
            @Override
            public int compare(T o1, T o2) {
                return getter.apply(o1).compareTo(getter.apply(o2));
            }
        };
    }
}

abstract class MyCmp<T> implements Comparator<T> {
    @Override
    public abstract int compare(T o1, T o2);
}