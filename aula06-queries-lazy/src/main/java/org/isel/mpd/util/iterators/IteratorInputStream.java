package org.isel.mpd.util.iterators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorInputStream implements Iterator<String> {
    private BufferedReader reader;
    private String line;

    public IteratorInputStream(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public boolean hasNext() {
        if(line != null){
            return true;
        }
        try {
            if ((line = reader.readLine()) != null){
                return true;
            }
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