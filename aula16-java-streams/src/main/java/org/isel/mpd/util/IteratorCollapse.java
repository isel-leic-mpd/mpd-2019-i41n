package org.isel.mpd.util;

import java.util.Iterator;

public class IteratorCollapse<T> implements Iterator<T> {

    private final Iterator<T> iter;

    public IteratorCollapse(Iterable<T> src) {
        iter = src.iterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public T next() {
        return iter.next();
    }
}
