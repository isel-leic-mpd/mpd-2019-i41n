package org.isel.mpd.util.queries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * V4 -- Generic Methods
 */
public class EagerQueries {

    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> pred){
        ArrayList<T> res = new ArrayList<>();
        for (T item : src) {
            if(pred.test(item))
                res.add(item);
        }
        return res;
    }

    public static <T> Iterable<T> skip(Iterable<T> src, int nr){
        ArrayList<T> res = new ArrayList<>();
        Iterator<T> iter = src.iterator();
        while(nr-- > 0 && iter.hasNext()) iter.next();
        while(iter.hasNext()) {
                res.add(iter.next());
        }
        return res;
    }

    public static <T, R> Iterable<R> map(Iterable<T> src, Function<T, R> mapper){
        ArrayList<R> res = new ArrayList<>();
        for (T item : src) {
            res.add(mapper.apply(item));
        }
        return res;
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
}
