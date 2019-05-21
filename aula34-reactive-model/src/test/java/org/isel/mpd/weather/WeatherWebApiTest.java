package org.isel.mpd.weather;

import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.time.LocalDate.parse;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


public class WeatherWebApiTest {
    @Test
    public void pastWeather() throws IOException {
        CompletableFuture<Stream<WeatherInfo>> infos = new WeatherRestfullApi()
            .pastWeather(37.017, -7.933, parse("2019-02-01"), parse("2019-03-28"));
        Iterator<WeatherInfo> iter = infos.join().iterator();
        assertNotNull(infos);
        assertTrue(iter.hasNext());
        WeatherInfo wi = iter.next();
        assertEquals(parse("2019-02-01"), wi.getDate());
        assertEquals(14, wi.getTempC());
        assertEquals(13.8, wi.getPrecipMM());
        assertEquals("Patchy rain possible", wi.getDesc());
    }

    @Test
    public void pastWeatherOnSeveralMonths() throws IOException {
        
    }
}