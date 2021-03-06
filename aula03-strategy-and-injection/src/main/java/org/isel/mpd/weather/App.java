package org.isel.mpd.weather;

import org.isel.mpd.weather.org.isel.mpd.util.HttpRequest;

import java.io.IOException;
import java.util.List;

import static java.time.LocalDate.parse;

public class App {

    public static void main(String[] args) throws IOException {
        WeatherWebApi webApi = new WeatherWebApi(new HttpRequest());
        /*
         * Get past weather at Lisbon between 2019-02-01 and 2019-02-10
         */
        List<WeatherInfo> infos = webApi
            .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-10"));
        for (WeatherInfo info : infos)
            System.out.println(info);
        /*
         * Get locations with name Oporto.
         */
        webApi
            .search("Oporto")
            .forEach(System.out::println);
    }
}
