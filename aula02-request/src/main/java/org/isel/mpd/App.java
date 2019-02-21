package org.isel.mpd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.parse;

public class App {

    public static void main(String[] args) throws IOException {
        List<WeatherInfo> infos = new WeatherWebApi()
            .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-10"));
        for (WeatherInfo info : infos)
            System.out.println(info);
    }
}
