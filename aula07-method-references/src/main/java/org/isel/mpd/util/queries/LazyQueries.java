package org.isel.mpd.util.queries;

import org.isel.mpd.util.iterators.IteratorFilter;
import org.isel.mpd.util.iterators.IteratorLimit;
import org.isel.mpd.util.iterators.IteratorMap;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * V5 -- Lazy implementations based on sequences (i.e. Iterable)
 */
public class LazyQueries {

    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred){
        return () -> new IteratorFilter(src, pred);
    }

    public static <T> Iterable<T> skip(Iterable<T> src, int nr){
        return () -> {
            Iterator<T> iter = src.iterator();
            int count = nr;
            while(count-- > 0 && iter.hasNext()) iter.next();
            return iter;
        };
    }

    public static <T> Iterable<T> limit(Iterable<T> src, int nr){
        return () -> new IteratorLimit<>(src,nr);
    }

    public static <T, R> Iterable<R> map(Iterable<T> src, Function<T, R> mapper){
        return () -> new IteratorMap(src, mapper);
    }

    public static <T> Iterable<T> generate(Supplier<T> next){
        return () -> new Iterator<T>() {
            public boolean hasNext() { return true; }
            public T next() { return next.get(); }
        };
    }

    public static <T> Iterable<T> iterate(T seed, Function<T, T> next){
        return () -> new Iterator<T>() {
            T curr = seed;
            public boolean hasNext() { return true; }
            public T next() {
                T tmp = curr;
                curr = next.apply(tmp);
                return tmp;
            }
        };
    }

    public static <T> int count(Iterable<T> src) {
        int count = 0;
        for (T item : src) {
            count++;
        }
        return count;
    }

    public static <T> Object[] toArray(Iterable<T> src) {
        LinkedList res = new LinkedList();
        for(T item : src) res.add(item);
        return res.toArray();
    }

    /**
     * This is not correct to return null when src is empty.
     * However for now we leave it like this.
     * Later we will fix it.
     */
    public static <T> T first(Iterable<T> src) {
        Iterator<T> iter = src.iterator();
        return iter.hasNext() ? iter.next() : null;
    }

    public static <T extends Comparable<T>> T max(Iterable<T> src) {
        Iterator<T> iter = src.iterator();
        T res = iter.next();
        while(iter.hasNext()) {
            T curr = iter.next();
            if(curr.compareTo(res) > 0)
                res = curr;
        }
        return res;
    }
}
