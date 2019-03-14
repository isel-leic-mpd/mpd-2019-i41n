package org.isel.mpd.weather;

import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.util.req.Request;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;
import org.junit.Assert;
import org.junit.Test;

import static org.isel.mpd.util.queries.LazyQueries.map;
import static org.isel.mpd.util.queries.LazyQueries.max;
import static org.isel.mpd.util.queries.LazyQueries.skip;

public class WeatherServiceTest {
    @Test
    public void testPastWeatherInOporto(){
        Request req = new HttpRequest();
        WeatherService service = new WeatherService(new WeatherWebApi(req));
        Iterable<Location> locals = service.search("oporto");
        Iterable<WeatherInfo> oportoWeather = skip(locals, 1).iterator().next().getPast30daysWeather();
        int tempC = max(map(oportoWeather, WeatherInfo::getTempC));
        Assert.assertEquals(22, tempC);
    }
}
