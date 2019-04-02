package org.isel.mpd.util.req;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractRequest implements Request {

    public final Stream<String> getLines(String path) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(openStream(path)));
        return reader.lines();
    }

    protected abstract InputStream openStream(String path);
}