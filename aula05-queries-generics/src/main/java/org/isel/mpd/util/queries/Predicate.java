package org.isel.mpd.util.queries;

public interface Predicate<T> {
    boolean test(T item);
}
