package org.isel.mpd.util.req;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRequest implements Request {

    public final Iterable<String> getLines(String path) {
        List<String> lstLines = new ArrayList<>();
        try {
            InputStream in = openStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                lstLines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lstLines;
    }

    protected abstract InputStream openStream(String path);
}