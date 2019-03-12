package org.isel.mpd.util.iterators;

import org.isel.mpd.util.queries.Supplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorInputStream implements Iterator<String> {
    private final Supplier<InputStream> in;
    private BufferedReader reader;
    private String line;
    private boolean isRead = false;

    public IteratorInputStream(Supplier<InputStream> in) {
        this.in = in;
    }

    private BufferedReader reader() {
        if(reader == null)
            this.reader = new BufferedReader(new InputStreamReader(in.get()));
        return reader;
    }

    @Override
    public boolean hasNext() {
        if(isRead) return false;
        if(line != null){
            return true;
        }
        try {
            if ((line = reader().readLine()) != null){
                return true;
            }
            isRead = true;
            reader().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public String next() {
        if(!hasNext()){ throw new NoSuchElementException();}
        String aux = line;
        line = null;
        return aux;
    }
}