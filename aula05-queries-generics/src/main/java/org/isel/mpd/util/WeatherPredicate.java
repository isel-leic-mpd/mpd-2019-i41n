package org.isel.mpd.util;

import org.isel.mpd.weather.WeatherInfo;

public interface WeatherPredicate {
    boolean test(WeatherInfo wi);
}
