package org.isel.mpd.weather;

import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.MockRequest;
import org.junit.Test;

import java.util.List;

import static java.lang.System.out;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.isel.mpd.util.queries.LazyQueries.count;
import static org.isel.mpd.util.queries.LazyQueries.filter;
import static org.isel.mpd.util.queries.LazyQueries.first;
import static org.isel.mpd.util.queries.LazyQueries.iterate;
import static org.isel.mpd.util.queries.LazyQueries.map;
import static org.isel.mpd.util.queries.LazyQueries.skip;
import static org.isel.mpd.util.queries.LazyQueries.toArray;
import static org.junit.Assert.assertArrayEquals;


public class LazyQueriesTest {

    @Test
    public void pastWeatherNumberOfSunnyDaysInFeb2019() {
        Iterable<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        assertEquals(
            8,
            count(LazyQueries.<WeatherInfo>filter(infos, wi -> wi.getDesc().equals("Sunny"))));
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
        Iterable<Integer> nrs = LazyQueries.limit(iterate(1, n -> n + 1), 4);
        int first = LazyQueries.first(filter(map(nrs,
                n -> n*n),
            n -> n > 3));
        assertEquals(4, first);
    }
}