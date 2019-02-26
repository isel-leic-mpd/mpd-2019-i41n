package org.isel.mpd.weather.org.isel.mpd.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MockRequest extends AbstractRequest {

    @Override
    protected InputStream openStream(String uri) {
    String[] parts = uri.split("/");
        String path = parts[parts.length-1]
                .replace('?', '-')
                .replace('&', '-')
                .replace('=', '-')
                .replace(',', '-')
                .substring(0,68);
        try {
            return ClassLoader.getSystemResource(path).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
