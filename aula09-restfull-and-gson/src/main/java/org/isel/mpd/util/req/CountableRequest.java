package org.isel.mpd.util.req;

import java.io.InputStream;

public class CountableRequest extends AbstractRequest{
    private int count;
    private AbstractRequest req;

    public CountableRequest(AbstractRequest req) {
        this.req = req;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    @Override
    protected InputStream openStream(String path) {
        count += 1;
        return req.openStream(path);
    }
}