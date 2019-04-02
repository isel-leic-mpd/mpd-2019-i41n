package org.isel.mpd.weather;

import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.time.LocalDate.now;

public class WeatherService {

    final WeatherApi api;

    public WeatherService(WeatherApi api) {
        this.api = api;
    }

    public WeatherService() {
        this(new WeatherRestfullApi(new HttpRequest()));
    }

    public Stream<Location> search(String query) {
        Stream<LocationInfo> locals = api.search(query);
        return locals.map(this::toLocation);
    }

    private Location toLocation(LocationInfo l) {
        Supplier<Stream<WeatherInfo>> wis = () -> api.pastWeather(
            l.getLatitude(),
            l.getLongitude(),
            now().minusDays(30),
            now());

        return new Location(
            l.getCountry(),
            l.getRegion(),
            l.getLatitude(),
            l.getLongitude(),
            wis);
    }
}
