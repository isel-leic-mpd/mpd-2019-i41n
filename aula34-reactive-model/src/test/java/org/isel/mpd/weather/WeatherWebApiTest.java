package org.isel.mpd.weather;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.reactivex.Observable.fromIterable;
import static java.time.LocalDate.parse;
import static junit.framework.Assert.assertEquals;


public class WeatherWebApiTest {
    @Test
    public void pastWeather() throws IOException {
        CompletableFuture<Stream<WeatherInfo>> infos = new WeatherRestfullApi()
            .pastWeather(37.017, -7.933, parse("2019-02-01"), parse("2019-03-28"));
        WeatherInfo wi = infos.join().findFirst().get();
        assertEquals(parse("2019-02-01"), wi.getDate());
        assertEquals(14, wi.getTempC());
        assertEquals(13.8, wi.getPrecipMM());
        assertEquals("Patchy rain possible", wi.getDesc());
    }

    @Test
    public void pastWeatherToObservable() throws IOException {
        CompletableFuture<Stream<WeatherInfo>> infos = new WeatherRestfullApi()
            .pastWeather(37.017, -7.933, parse("2019-02-01"), parse("2019-03-28"));
        Observable<WeatherInfo> wis = Observable
            .fromFuture(infos)
            .flatMap(strm -> fromIterable(strm::iterator));
        WeatherInfo wi = wis.firstElement().blockingGet();
        assertEquals(parse("2019-02-01"), wi.getDate());
        assertEquals(14, wi.getTempC());
        assertEquals(13.8, wi.getPrecipMM());
        assertEquals("Patchy rain possible", wi.getDesc());
    }


    @Test
    public void pastWeatherOnSeveralMonths() throws IOException {
        LocalDate from = LocalDate.of(2018, 12, 7);
        LocalDate to = LocalDate.of(2019, 3, 19);
        Stream
            .iterate(from, prev -> prev.withDayOfMonth(1).plusMonths(1))
            .peek(dt -> System.out.print(dt + " -- "))
            .takeWhile(dt -> dt.isBefore(to))
            .map(dt -> min(dt.withDayOfMonth(dt.lengthOfMonth()), to))
            .forEach(dt -> System.out.println(dt));
    }

    private LocalDate min(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? a : b;
    }
}