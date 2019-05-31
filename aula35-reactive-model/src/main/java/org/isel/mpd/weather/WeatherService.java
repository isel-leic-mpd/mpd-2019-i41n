package org.isel.mpd.weather;

import io.reactivex.Observable;
import io.reactivex.internal.functions.Functions;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WeatherService implements AutoCloseable{

    final WeatherWebApi webApi = new WeatherWebApi();

    public Observable<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        Stream<CompletableFuture<Stream<WeatherInfo>>> cfs = Stream
            .iterate(from, prev -> prev.withDayOfMonth(1).plusMonths(1))
            .takeWhile(dt -> dt.isBefore(to)) // Stream<LocalDate>
            .map(start -> {
                LocalDate end = min(start.withDayOfMonth(start.lengthOfMonth()), to);
                return webApi.pastWeather(lat, log, start, end);
            });// Stream<CF<Stream<WeatherInfo>>>

        return Observable
            .fromIterable(cfs::iterator)
            .map(WeatherService::toObservable)
            .flatMap(Functions.identity())
            .flatMap(strm -> Observable.fromIterable(strm::iterator));
    }
    private LocalDate min(LocalDate a, LocalDate b) {
        return a.isBefore(b) ? a : b;
    }

    public static <T> Observable<T> toObservable(CompletableFuture<T> future) {
        return Observable.create(subscriber ->
            future.whenComplete((result, error) -> {
                if (error != null) {
                    subscriber.onError(error);
                } else {
                    subscriber.onNext(result);
                    subscriber.onComplete();
                }
            }));
    }

    @Override
    public void close() throws Exception {
        webApi.close();
    }
}
