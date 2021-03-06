package org.isel.mpd.weather;

import junit.framework.Assert;
import org.isel.mpd.util.MockRequest;
import org.junit.Test;

import java.util.List;

import static java.time.LocalDate.parse;
import static junit.framework.Assert.*;
import static org.isel.mpd.util.Queries.filterWeather;


public class WeatherWebApiTest {

    @Test
    public void pastWeatherNumberOfSunnyDaysInFeb2019() {
        List<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
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
        List<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        // assertEquals(2, Queries.filterByDesc(infos, "Light rain shower").size());
        assertEquals(2, filterWeather(infos, wi -> wi.getDesc().equals("Light rain shower")).size());

    }

    @Test
    public void pastWeather() {
        List<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));

        assertNotNull(infos);
        assertFalse(infos.isEmpty());
        assertTrue(infos.size() > 0);
        WeatherInfo wi = infos.get(0);
        assertEquals(parse("2019-02-01"), wi.date);
        assertEquals(14, wi.tempC);
        assertEquals(13.8, wi.precipMM);
        assertEquals("Patchy rain possible", wi.desc);
    }

    @Test
    public void search() {
        List<LocationInfo> infos = new WeatherWebApi(new MockRequest())
                .search("Oporto");
        assertNotNull(infos);
        assertFalse(infos.isEmpty());
        assertTrue(infos.size() > 0);
        LocationInfo li = infos.get(1);
        assertEquals(li.getCountry(), "Portugal");
        assertEquals(li.getRegion(), "Porto");
        assertEquals(li.getLatitude(), 41.15);
        assertEquals(li.getLongitude(), -8.617);
    }

}