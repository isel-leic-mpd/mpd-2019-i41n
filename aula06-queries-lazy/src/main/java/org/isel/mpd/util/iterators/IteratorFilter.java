package org.isel.mpd.util.iterators;

import org.isel.mpd.util.queries.Predicate;

import java.util.Iterator;

public class IteratorFilter<T>  implements Iterator<T> {

    public IteratorFilter(Iterable<T> iter, Predicate<T> pred) {
    }
    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("To DO: implement hasNext() of IteratorFilter!");
    }

    @Override
    public T next() {
        throw new UnsupportedOperationException("To DO: implement next() of IteratorFilter!");
    }
}
