package org.isel.mpd.weather.model;

import org.isel.mpd.weather.dto.WeatherInfo;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Location {

    private final String country;
    private final String region;
    private final double latitude;
    private final double longitude;
    private final Supplier<Stream<WeatherInfo>> past30daysWeather;

    public Location(String country, String region, double latitude, double longitude, Supplier<Stream<WeatherInfo>> past30daysWeather) {
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.past30daysWeather = past30daysWeather;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Stream<WeatherInfo> getPast30daysWeather() {
        return past30daysWeather.get();
    }
}
