package org.isel.mpd.weather;

import org.isel.mpd.util.req.MockRequest;
import org.isel.mpd.util.queries.EagerQueries;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.junit.Test;

import java.util.List;

import static java.lang.System.out;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.isel.mpd.util.queries.EagerQueries.count;
import static org.isel.mpd.util.queries.EagerQueries.filter;
import static org.isel.mpd.util.queries.EagerQueries.first;
import static org.isel.mpd.util.queries.EagerQueries.map;
import static org.isel.mpd.util.queries.EagerQueries.skip;
import static org.isel.mpd.util.queries.EagerQueries.toArray;
import static org.junit.Assert.assertArrayEquals;


public class EagerQueriesTest {

    @Test
    public void pastWeatherNumberOfSunnyDaysInFeb2019() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        assertEquals(
            8,
            count(EagerQueries.<WeatherInfo>filter(infos, wi -> wi.getDesc().equals("Sunny"))));
            // <=> Queries.filter(infos ... ) // T é INFERIDO a partir de infos.
    }

    @Test
    public void pastWeatherNumberOfRainnnyDaysInFeb2019() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        assertEquals(2, count(filter(infos, wi -> wi.getDesc().equals("Light rain shower"))));
    }
    @Test
    public void testSkip(){
        List<Integer> nrs = asList(1, 2, 3, 4, 5, 6, 7, 8);
        Object[] actual = toArray(skip(nrs, 3));
        Object[] expected = { 4, 5, 6, 7, 8 };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testMap(){
        List<String> words = asList("super", "isel", "ola", "fcp");
        Object[] actual = toArray(map(words, w -> w.length()));
        Object[] expected = { 5, 4, 3, 3 };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFirstFilterMap(){
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        int tempC = first(filter(map(infos,
                                wi -> { out.println("map"); return wi.getTempC();}),
                            t -> { out.println("filter"); return t > 14;}));
        assertEquals(16, tempC);
    }

    @Test
    public void testFirstFilterMapNrs(){
        List<Integer> nrs = asList(1, 2, 3, 4);
        int first = EagerQueries.first(filter(map(nrs,
                n -> n*n),
            n -> n > 3));
        assertEquals(4, first);
    }
}