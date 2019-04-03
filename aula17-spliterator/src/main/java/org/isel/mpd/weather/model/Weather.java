package org.isel.mpd.weather.model;

import java.time.LocalDate;

public class Weather {
    final LocalDate date;
    final int tempC;
    final double precipMM;
    final String desc;

    public Weather(LocalDate date, int tempC, double precipMM, String desc) {
        this.date = date;
        this.tempC = tempC;
        this.precipMM = precipMM;
        this.desc = desc;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTempC() {
        return tempC;
    }

    public double getPrecipMM() {
        return precipMM;
    }

    public String getDesc() {
        return desc;
    }
}
