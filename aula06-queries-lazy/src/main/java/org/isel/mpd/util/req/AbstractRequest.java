package org.isel.mpd.util.req;

import org.isel.mpd.util.iterators.IteratorInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRequest implements Request {

    public final Iterable<String> getLines(String path) {
        try (InputStream in = openStream(path)){
            return () -> new IteratorInputStream(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract InputStream openStream(String path);
}