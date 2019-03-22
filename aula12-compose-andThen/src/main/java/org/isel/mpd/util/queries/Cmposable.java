package org.isel.mpd.util.queries;

import java.util.Comparator;
import java.util.function.Function;

public abstract class Cmposable<T> implements Comparator<T> {
    @Override
    public abstract int compare(T o1, T o2);

}