package org.isel.mpd.weather;

import io.reactivex.Observable;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static junit.framework.Assert.assertEquals;

public class WeatherServiceTest {
    @Test
    public void pastWeatherOnSeveralMonths() throws IOException {
        WeatherService weather = new WeatherService();
        LocalDate[] from = {LocalDate.of(2018, 12, 7)};
        LocalDate to = LocalDate.of(2019, 3, 19);
        Observable<WeatherInfo> infos = weather.pastWeather(37.017, -7.933, from[0], to);
        System.out.println(Thread.currentThread().getId());
        CompletableFuture cf = new CompletableFuture();
        infos.doOnNext(info -> {
            assertEquals(from[0], info.date);
            from[0] = from[0].plusDays(1);
            System.out.println(Thread.currentThread().getId());
        })
        .doOnComplete(() -> cf.complete(null))
        .doOnError(err -> cf.completeExceptionally(err))
        .subscribe();
        cf.join();
        assertEquals(from[0], to.plusDays(1));
    }
}
