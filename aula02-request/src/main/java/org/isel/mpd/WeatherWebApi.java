package org.isel.mpd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherWebApi {
    final String HOST = "http://api.worldweatheronline.com/premium/v1/";
    final String PATH = "past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";
    final String WEATHER_KEY = "10a7e54b547c4c7c870162131192102";

    public List<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        String path = HOST + String.format(PATH, lat, log, from, to, WEATHER_KEY);
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        List<WeatherInfo> infos = new ArrayList<>();
        try(
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            String line;
            while (reader.readLine().startsWith("#")) { } // Skip comments
            reader.readLine();                            // Not Available
            boolean isOdd = true;
            while ((line = reader.readLine()) != null) {
                if (isOdd) {
                    infos.add(WeatherInfo.valueOf(line));
                }
                isOdd = !isOdd;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return infos;
    }
}
