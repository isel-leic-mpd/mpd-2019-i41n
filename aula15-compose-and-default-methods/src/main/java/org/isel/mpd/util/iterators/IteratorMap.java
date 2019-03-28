package org.isel.mpd.util.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorMap<T, R> implements Iterator<R> {
    final Iterator<T> src;
    final Function<T, R> mapper;

    public IteratorMap(Iterable<T> src, Function<T, R> mapper) {
        this.src = src.iterator();
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return src.hasNext();
    }

    @Override
    public R next() {
        return mapper.apply(src.next());
    }
}
