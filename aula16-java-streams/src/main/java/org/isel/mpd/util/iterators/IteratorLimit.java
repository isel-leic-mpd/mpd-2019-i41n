package org.isel.mpd.util.iterators;

import java.util.Iterator;

public class IteratorLimit<T> implements Iterator<T> {
    private final Iterator<T> iter;
    private final int limit;
    private int count;

    public IteratorLimit(Iterable<T> src, int limit) {
        this.iter = src.iterator();
        this.limit = limit;
        count = 0;
    }

    @Override
    public boolean hasNext() {
        if(count < limit)
            return iter.hasNext();
        return false;
    }

    @Override
    public T next() {
        count++;
        return iter.next();
    }
}
