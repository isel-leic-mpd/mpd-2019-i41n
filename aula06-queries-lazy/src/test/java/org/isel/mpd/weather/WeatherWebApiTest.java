package org.isel.mpd.weather;

import org.isel.mpd.util.req.MockRequest;
import org.junit.Test;

import java.util.Iterator;

import static java.time.LocalDate.parse;
import static junit.framework.Assert.*;


public class WeatherWebApiTest {
    @Test
    public void pastWeather() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        Iterator<WeatherInfo> iter = infos.iterator();
        assertNotNull(infos);
        assertTrue(iter.hasNext());
        WeatherInfo wi = iter.next();
        assertEquals(parse("2019-02-01"), wi.date);
        assertEquals(14, wi.tempC);
        assertEquals(13.8, wi.precipMM);
        assertEquals("Patchy rain possible", wi.desc);
    }

    @Test
    public void search() {
        Iterable<LocationInfo> infos = new WeatherWebApi(new MockRequest())
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