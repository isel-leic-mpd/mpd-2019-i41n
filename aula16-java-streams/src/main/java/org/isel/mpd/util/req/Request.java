package org.isel.mpd.util.req;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Request {

    public Stream<String> getLines(String path);
}
