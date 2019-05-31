package org.isel.mpd;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class TimeController {
    final TimeView view = new TimeView();

    public TimeController(Router router) {
        router.route("/time").handler(this::timeHandler);
    }

    public void timeHandler(RoutingContext ctx) {
        // This handler gets called for each request
        // that arrives on /time path
        HttpServerResponse response = ctx.response();
        view.write(response);
    }
}
