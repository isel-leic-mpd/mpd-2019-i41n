package org.isel.mpd.weather;


import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface WeatherApi {

    Stream<LocationInfo> search(String query);

    Stream<WeatherInfo> pastWeather(double lat,
                                      double log,
                                      LocalDate from,
                                      LocalDate to);
}
