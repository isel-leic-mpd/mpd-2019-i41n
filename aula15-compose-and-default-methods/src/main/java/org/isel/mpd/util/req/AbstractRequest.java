package org.isel.mpd.util.req;

import org.isel.mpd.util.iterators.IteratorInputStream;

import java.io.InputStream;
import java.util.function.Supplier;

public abstract class AbstractRequest implements Request {

    public final Iterable<String> getLines(String path) {

        return () -> {
            Supplier<InputStream> in = () -> openStream(path);
            return new IteratorInputStream(in);
        };
    }

    protected abstract InputStream openStream(String path);
}