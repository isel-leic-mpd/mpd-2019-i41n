package org.isel.mpd.weather;

import io.reactivex.Observable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WeatherService {

    public Observable<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        throw new UnsupportedOperationException();
    }
}
