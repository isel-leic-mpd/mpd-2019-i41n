package org.javasync.idioms;

import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.javasync.restdto.PastWeatherDataWeatherDto;
import org.javasync.restdto.PastWeatherDto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WeatherWebApi {
    final static String HOST = "http://api.worldweatheronline.com/premium/v1/";
    final static String PATH_PAST_WEATHER = "past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=json&key=%s";
    final static String WEATHER_KEY = "da5f2b8cc0a84eef8c6173655190905";
    final static Gson gson = new Gson();

    public static CompletableFuture<List<WeatherInfo>> pastWeather(double lat, double log, LocalDate from, LocalDate to) throws IOException {
        String path = HOST + String.format(PATH_PAST_WEATHER, lat, log, from, to, WEATHER_KEY);
        AsyncHttpClient ahc = Dsl.asyncHttpClient();
        return ahc
            .prepareGet(path)
            .execute()
            .toCompletableFuture()
            .thenApply(Response::getResponseBody)
            .whenComplete((body, err) -> close(ahc))
            .thenApply(body -> {
                PastWeatherDto dto = gson.fromJson(body, PastWeatherDto.class);
                return fromDtoToWeatherInfo(dto);
            });
    }

    private static void close(AsyncHttpClient ahc) {
        try {
            ahc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<WeatherInfo> fromDtoToWeatherInfo(PastWeatherDto dto) {
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
            })
            .collect(toList());
    }
}
