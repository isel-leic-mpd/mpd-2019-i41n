package org.isel.mpd.weather;

import org.isel.mpd.util.req.MockRequest;
import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;
import static junit.framework.Assert.*;


public class WeatherWebApiTest {
    @Test
    public void pastWeather() {
        Stream<WeatherInfo> infos = new WeatherRestfullApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        Iterator<WeatherInfo> iter = infos.iterator();
        assertNotNull(infos);
        assertTrue(iter.hasNext());
        WeatherInfo wi = iter.next();
        assertEquals(parse("2019-02-01"), wi.getDate());
        assertEquals(14, wi.getTempC());
        assertEquals(13.8, wi.getPrecipMM());
        assertEquals("Patchy rain possible", wi.getDesc());
    }

    @Test
    public void search() {
        Stream<LocationInfo> infos = new WeatherRestfullApi(new MockRequest())
                .search("Oporto");
        Iterator<LocationInfo> iter = infos.iterator();
        assertNotNull(infos);
        assertTrue(iter.hasNext());
        LocationInfo li = iter.next();
        assertEquals(li.getCountry(), "Spain");
        assertEquals(li.getRegion(), "Galicia");
        assertEquals(li.getLatitude(), 42.383);
        assertEquals(li.getLongitude(), -7.100);
    }

}