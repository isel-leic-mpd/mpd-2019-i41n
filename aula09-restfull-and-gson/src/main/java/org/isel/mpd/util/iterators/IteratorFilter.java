package org.isel.mpd.util.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorFilter<T> implements Iterator<T> {
    private T next = null;

    private Iterator<T> src;
    private Predicate<T> p;

    public IteratorFilter(Iterable<T> iter, Predicate<T> pred) {
        this.src = iter.iterator();
        this.p = pred;
    }

    @Override
    public boolean hasNext() {
        if (next != null) return true;

        while (src.hasNext()) {
            T item = src.next();

            if (p.test(item)) {
                this.next = item;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();

        T aux = next;
        next = null;
        return aux;
    }
}