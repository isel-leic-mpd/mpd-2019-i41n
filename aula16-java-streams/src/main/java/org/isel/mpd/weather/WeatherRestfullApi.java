package org.isel.mpd.weather;

import com.google.gson.Gson;
import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.util.req.Request;
import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.restdto.PastWeatherDataWeatherDto;
import org.isel.mpd.weather.restdto.PastWeatherDto;
import org.isel.mpd.weather.restdto.SearchApiResultDto;
import org.isel.mpd.weather.restdto.SearchDto;

import java.time.LocalDate;
import java.util.Arrays;

import static org.isel.mpd.util.queries.LazyQueries.from;
import static org.isel.mpd.util.queries.LazyQueries.map;

public class WeatherRestfullApi implements WeatherApi{
    final String HOST = "http://api.worldweatheronline.com/premium/v1/";
    final String PATH_PAST_WEATHER = "past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=json&key=%s";
    final String PATH_SEARCH = "search.ashx?query=%s&format=json&key=%s";
    final String WEATHER_KEY = "10a7e54b547c4c7c870162131192102";

    private final Request req;
    private final Gson gson;

    public WeatherRestfullApi() {
        this(new HttpRequest(), new Gson());
    }

    public WeatherRestfullApi(Request req, Gson gson) {
        this.req = req;
        this.gson = gson;
    }

    public WeatherRestfullApi(Request req) {
        this(req, new Gson());
    }

    @Override
    public Iterable<LocationInfo> search(String query) {
        String path = HOST + String.format(PATH_SEARCH, query, WEATHER_KEY);
        Iterable<String> src = req.getLines(path);
        String body = String.join("", src);
        SearchDto dto = gson.fromJson(body, SearchDto.class);
        SearchApiResultDto[] locations = dto.getSearch_api().getResult();
        return LazyQueries.map(LazyQueries.from(locations), l -> new LocationInfo(
                l.getCountry()[0].getValue(),
                l.getRegion()[0].getValue(),
                Double.parseDouble(l.getLatitude()),
                Double.parseDouble(l.getLongitude())));
    }

    @Override
    public Iterable<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        String path = HOST + String.format(PATH_PAST_WEATHER, lat, log, from, to, WEATHER_KEY);
        Iterable<String> src = req.getLines(path);
        String body = String.join("", src);
        PastWeatherDto dto = gson.fromJson(body, PastWeatherDto.class);
        PastWeatherDataWeatherDto[] weather = dto.getData().getWeather();
        return map(from(weather), w -> {
            var h = w.getHourly()[0];
            return new WeatherInfo(
                    LocalDate.parse(w.getDate()),
                    h.getTempC(),
                    h.getPrecipMM(),
                    h.getWeatherDesc());
        });
    }
}
