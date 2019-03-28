package org.isel.mpd.util.queries;

import java.util.Comparator;
import java.util.function.Function;

@FunctionalInterface
public interface Cmposable<T> extends Comparator<T> {
    @Override
    public abstract int compare(T o1, T o2);

    public default <S extends Comparable<S>> Cmposable<T> andThen(Function<T, S> getter2) {
        return (o1, o2) -> {
            int res = compare(o1, o2);
            return res != 0
                ? res
                : getter2.apply(o1).compareTo(getter2.apply(o2));
        };
    }
}