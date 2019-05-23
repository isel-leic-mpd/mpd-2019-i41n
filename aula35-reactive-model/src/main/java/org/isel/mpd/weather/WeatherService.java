package org.isel.mpd.weather;

import io.reactivex.Observable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WeatherService {

    final WeatherWebApi webApi = new WeatherWebApi();

    public Observable<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        Stream
            .iterate(from, prev -> prev.withDayOfMonth(1).plusMonths(1))
            .takeWhile(dt -> dt.isBefore(to)) // Stream<LocalDate>
            .map(start -> {
                LocalDate end = min(start.withDayOfMonth(start.lengthOfMonth()), to);
                return webApi.pastWeather(lat, log, start, end);
            }); // Stream<CF<Stream<WeatherInfo>>>


        return null;
    }
    private LocalDate min(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? a : b;
    }
}
