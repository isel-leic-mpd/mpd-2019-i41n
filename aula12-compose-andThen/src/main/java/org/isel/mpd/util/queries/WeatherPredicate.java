package org.isel.mpd.util.queries;

import org.isel.mpd.weather.dto.WeatherInfo;

public interface WeatherPredicate {
    boolean test(WeatherInfo wi);
}
