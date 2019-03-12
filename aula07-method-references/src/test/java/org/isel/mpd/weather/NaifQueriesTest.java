package org.isel.mpd.weather;

import org.isel.mpd.util.req.MockRequest;
import org.junit.Test;

import static java.time.LocalDate.parse;
import static junit.framework.Assert.assertEquals;
import static org.isel.mpd.util.queries.NaifQueries.filterWeather;


public class NaifQueriesTest {

    @Test
    public void pastWeatherNumberOfSunnyDaysInFeb2019() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        // assertEquals(8, Queries.filterByDesc(infos, "Sunny").size());
        /*
        List<WeatherInfo> res = Queries.filterWeather(infos, new WeatherPredicate() {
            @Override
            public boolean test(WeatherInfo wi) {
                return wi.getDesc().equals("Sunny");
            }
        });
        */
        assertEquals(8, filterWeather(infos, wi -> wi.getDesc().equals("Sunny")).size());
    }

    @Test
    public void pastWeatherNumberOfRainnnyDaysInFeb2019() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        // assertEquals(2, Queries.filterByDesc(infos, "Light rain shower").size());
        assertEquals(2, filterWeather(infos, wi -> wi.getDesc().equals("Light rain shower")).size());

    }
}