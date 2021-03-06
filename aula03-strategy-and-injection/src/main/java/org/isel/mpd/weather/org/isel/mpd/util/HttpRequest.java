package org.isel.mpd.weather.org.isel.mpd.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpRequest extends AbstractRequest {

    @Override
    protected InputStream openStream(String path) {
        try {
            return new URL(path).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}