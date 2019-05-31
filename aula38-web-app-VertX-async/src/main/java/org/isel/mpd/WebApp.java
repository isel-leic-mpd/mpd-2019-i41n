package org.isel.mpd;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class WebApp {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        new TimeController(router);    // Add Routes = an handler for each path (e.g. /time)
        new WeatherController(router); // Add Routes = an handler for each path (e.g. /pastweather)
        server.requestHandler(router).listen(3000);
    }
}
