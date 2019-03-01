package org.isel.mpd.util.queries;

public interface Function<T, R> {
    R apply(T item);
}
