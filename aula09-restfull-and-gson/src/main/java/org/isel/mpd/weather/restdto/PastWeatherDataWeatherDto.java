package org.isel.mpd.weather.restdto;

public class PastWeatherDataWeatherDto {
    private final String date;
    private final PastWeatherDataWeatherHourlyDto[] hourly;

    public PastWeatherDataWeatherDto(String date, PastWeatherDataWeatherHourlyDto[] hourly) {
        this.date = date;
        this.hourly = hourly;
    }

    public String getDate() {
        return date;
    }

    public PastWeatherDataWeatherHourlyDto[] getHourly() {
        return hourly;
    }
}
