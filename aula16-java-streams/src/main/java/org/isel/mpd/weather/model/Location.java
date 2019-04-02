package org.isel.mpd.weather.model;

import org.isel.mpd.weather.dto.WeatherInfo;

public class Location {

    private final String country;
    private final String region;
    private final double latitude;
    private final double longitude;
    private final Iterable<WeatherInfo>  past30daysWeather;

    public Location(String country, String region, double latitude, double longitude, Iterable<WeatherInfo> past30daysWeather) {
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

    public Iterable<WeatherInfo> getPast30daysWeather() {
        return past30daysWeather;
    }
}
