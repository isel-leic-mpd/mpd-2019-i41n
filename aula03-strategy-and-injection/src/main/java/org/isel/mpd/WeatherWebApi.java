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
    final String PATH_PAST_WEATHER = "past-weather.ashx?q=%s,%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";
    final String PATH_SEARCH = "search.ashx?query=%s&format=tab&key=%s";
    final String WEATHER_KEY = "10a7e54b547c4c7c870162131192102";

    /**
     * E.g. http://api.worldweatheronline.com/premium/v1/past-weather.ashx?q=37.017,-7.933&date=2019-01-01&enddate=2019-01-30&tp=24&format=csv&key=10a7e54b547c4c7c870162131192102
     *
     * @param lat Location latitude
     * @param log Location longitude
     * @param from Beginning date
     * @param to End date
     * @return List of WeatherInfo objects with weather information.
     */
    public List<WeatherInfo> pastWeather(double lat, double log, LocalDate from, LocalDate to) {
        String path = HOST + String.format(PATH_PAST_WEATHER, lat, log, from, to, WEATHER_KEY);
        List<WeatherInfo> infos = new ArrayList<>();
        try(
            InputStream in = new URL(path).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            String line;
            while (reader.readLine().startsWith("#")) { } // Skip comments
            reader.readLine();                            // Skip Not Available
            while ((line = reader.readLine()) != null) {
                infos.add(WeatherInfo.valueOf(line));
                reader.readLine();                        // Skip daily information
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return infos;
    }

    /**
     * e.g. http://api.worldweatheronline.com/premium/v1/search.ashx?query=Oporto&format=tab&key=10a7e54b547c4c7c870162131192102
     *
     * @param query Name of the city you are looking for.
     * @return List of LocationInfo objects with location information.
     */
    public List<LocationInfo> search(String query) {
        String path = HOST + String.format(PATH_SEARCH, query, WEATHER_KEY);
        System.out.println(path);
        List<LocationInfo> locations = new ArrayList<>();
        try(
            InputStream in = new URL(path).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("#")) continue;
                locations.add(LocationInfo.valueOf(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }
}
