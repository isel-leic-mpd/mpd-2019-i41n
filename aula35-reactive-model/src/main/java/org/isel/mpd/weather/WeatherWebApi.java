package org.isel.mpd.weather;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.isel.mpd.weather.restdto.PastWeatherDataWeatherDto;
import org.isel.mpd.weather.restdto.PastWeatherDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class WeatherWebApi implements AutoCloseable{

    final String HOST = "http://api.worldweatheronline.com/premium/v1/";
    final String PATH_PAST_WEATHER = "past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=json&key=%s";
    final String PATH_SEARCH = "search.ashx?query=%s&format=json&key=%s";
    final String WEATHER_KEY = "da5f2b8cc0a84eef8c6173655190905";

    private final Gson gson = new Gson();
    private final AsyncHttpClient ahc = Dsl.asyncHttpClient();

    public CompletableFuture<Stream<WeatherInfo>> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        String path = HOST + String.format(PATH_PAST_WEATHER, lat, log, from, to, WEATHER_KEY);
        System.out.println(path);
        return ahc
            .prepareGet(path)
            .execute()
            .toCompletableFuture()
            .thenApply(Response::getResponseBody)
            .thenApply(body -> {
                PastWeatherDto dto = gson.fromJson(body, PastWeatherDto.class);
                return fromDtoToWeatherInfo(dto);
            });
    }

    private static Stream<WeatherInfo> fromDtoToWeatherInfo(PastWeatherDto dto) {
        PastWeatherDataWeatherDto[] weather = dto.getData().getWeather();
        return Stream
            .of(weather)
            .map(w -> {
                var h = w.getHourly()[0];
                return new WeatherInfo(
                        LocalDate.parse(w.getDate()),
                        h.getTempC(),
                        h.getPrecipMM(),
                        h.getWeatherDesc());
            });
    }

    @Override
    public void close() throws Exception {
        ahc.close();
    }
}
