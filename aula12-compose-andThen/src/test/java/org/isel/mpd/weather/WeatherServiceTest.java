package org.isel.mpd.weather;

import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.util.req.Request;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

import static org.isel.mpd.util.queries.Cmp.comparing;
import static org.isel.mpd.util.queries.LazyQueries.count;
import static org.isel.mpd.util.queries.LazyQueries.first;
import static org.isel.mpd.util.queries.LazyQueries.map;
import static org.isel.mpd.util.queries.LazyQueries.max;
import static org.isel.mpd.util.queries.LazyQueries.skip;

public class WeatherServiceTest {
    @Test
    public void testPastWeatherInOporto(){
        // CountableRequest req = new CountableRequest(new HttpRequest());
        Request r = new HttpRequest();

        int[] count = {0};
        Function<String, String> counter = path -> {
            count[0]++;
            System.out.println("Request " + path);
            return path;
        };
        Function<String, Iterable<String>> req = counter.andThen(r::getLines);

        WeatherService service = new WeatherService(new WeatherRestfullApi(req::apply));
        Iterable<Location> locals = service.search("oporto");
        Assert.assertEquals(1, count[0]);

        Iterable<WeatherInfo> oportoWeather = first(skip(locals, 1)).getPast30daysWeather();
        int tempC = max(map(oportoWeather, WeatherInfo::getTempC));

        Assert.assertEquals(22, tempC);
        Assert.assertEquals(2, count[0]);

        count(locals); // + 6 requests of pastWeather for 6 locations
        Assert.assertEquals(2, count[0]);
    }

    @Test
    public void testDayWithMaxTemperatureInOporto(){
        WeatherService service = new WeatherService(new WeatherRestfullApi());
        Iterable<Location> locals = service.search("oporto");
        Iterable<WeatherInfo> oportoWeather = first(skip(locals, 1)).getPast30daysWeather();
        // WeatherInfo maxTemp = max(oportoWeather, (w1, w2) -> w1.getTempC() - w2.getTempC());
        WeatherInfo maxTemp = max(oportoWeather, comparing(WeatherInfo::getTempC));
        // max(oportoWeather, (w1, w2) -> w1.getPrecipMM() - w2.getPrecipMM());
        max(oportoWeather, comparing(WeatherInfo::getPrecipMM).andThen(WeatherInfo::getTempC));

    }
}
