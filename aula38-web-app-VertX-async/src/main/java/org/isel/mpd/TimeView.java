package org.isel.mpd;

import htmlflow.StaticHtml;
import io.vertx.core.http.HttpServerResponse;

import static java.lang.String.format;
import static java.time.Instant.now;

public class TimeView implements View{
    @Override
    public void write(HttpServerResponse resp, Object model) {
        throw new UnsupportedOperationException("This view does not require a Model. You should invoke write(resp) instead!");
    }

    @Override
    public void write(HttpServerResponse resp) {
        String currTime = format("Current date and time is %s", now());
        String html = StaticHtml.view()
            .html()
                .body()
                    .h1()
                        .text(currTime)
                    .__() // h1
                .__() // body
            .__() // html
        .render();

        resp.putHeader("content-type", "text/html");
        // write to the response and end it
        resp.end(html);
    }
}
