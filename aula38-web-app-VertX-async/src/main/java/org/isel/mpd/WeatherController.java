package org.isel.mpd;

import io.reactivex.Observable;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.isel.mpd.weather.WeatherInfo;
import org.isel.mpd.weather.WeatherService;

import java.time.LocalDate;

import static java.lang.Double.parseDouble;

public class WeatherController implements AutoCloseable{

    final WeatherService weather = new WeatherService();
    final WeatherPastView view = new WeatherPastView();

    public WeatherController(Router router) {
        router.route("/pastweather").handler(this::pastWeather);
    }

    private void pastWeather(RoutingContext ctx) {
        // get parameters from HTTP request
        String lat = ctx.request().getParam("lat");
        String log = ctx.request().getParam("log");
        String from = ctx.request().getParam("from");
        String to = ctx.request().getParam("to");
        // call service
        Observable<WeatherInfo> infos = weather.pastWeather(
            parseDouble(lat), parseDouble(log), LocalDate.parse(from), LocalDate.parse(to));
        // transform it to HTML and send response
        view.write(ctx.response(), infos);
    }

    @Override
    public void close() throws Exception {
        // weather.close();
    }
}
