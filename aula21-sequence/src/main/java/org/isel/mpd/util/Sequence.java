package org.isel.mpd.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Sequence<T> {

    boolean tryAdvance(Consumer<T> cons);

    public static <T> Sequence<T> of(T...arr) {
        int[] idx = {0};
        return cons -> {
            if(idx[0] >= arr.length) return false;
            cons.accept(arr[idx[0]]);
            idx[0]++;
            return true;
        };
    }

    public static <T> Sequence<T> of(Iterable<T> src) {
        Iterator<T> iter = src.iterator();
        return cons -> {
            if(!iter.hasNext()) return false;
            cons.accept(iter.next());
            return true;
        };
    }

    public static <T> Sequence<T> of(Stream<T> src) {
        return src.spliterator()::tryAdvance;
    }

    public static <T> Sequence<T> empty(){
        return __ -> false;
    }

    public default <R> Sequence<R> map(Function<T, R> mapper) {
        return cons -> tryAdvance(item -> cons.accept(mapper.apply(item)));
    }

    public default void forEach(Consumer<T> cons) {
        while(tryAdvance(cons)) {}
    }
}
