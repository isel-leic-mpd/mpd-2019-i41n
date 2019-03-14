package org.isel.mpd.weather;

import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.CountableRequest;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.util.req.Request;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;
import org.junit.Assert;
import org.junit.Test;

import static org.isel.mpd.util.queries.LazyQueries.count;
import static org.isel.mpd.util.queries.LazyQueries.map;
import static org.isel.mpd.util.queries.LazyQueries.max;
import static org.isel.mpd.util.queries.LazyQueries.skip;

public class WeatherServiceTest {
    @Test
    public void testPastWeatherInOporto(){
        // CountableRequest req = new CountableRequest(new HttpRequest());
        Request r = new HttpRequest();
        int[] count = {0};
        Request req = path -> {
            count[0]++;
            return r.getLines(path);
        };

        WeatherService service = new WeatherService(new WeatherWebApi(req));
        Iterable<Location> locals = service.search("oporto");
        Assert.assertEquals(1, count[0]);

        Iterable<WeatherInfo> oportoWeather = skip(locals, 1).iterator().next().getPast30daysWeather();
        int tempC = max(map(oportoWeather, WeatherInfo::getTempC));

        Assert.assertEquals(22, tempC);
        Assert.assertEquals(3, count[0]);

        count(locals); // + 6 requests of pastWeather for 6 locations
        Assert.assertEquals(9, count[0]);
    }
}
