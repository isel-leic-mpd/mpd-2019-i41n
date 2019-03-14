package org.isel.mpd.weather;


import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;

import java.time.LocalDate;

public interface WeatherApi {

    Iterable<LocationInfo> search(String query);

    Iterable<WeatherInfo> pastWeather(double lat,
                                      double log,
                                      LocalDate from,
                                      LocalDate to);
}
