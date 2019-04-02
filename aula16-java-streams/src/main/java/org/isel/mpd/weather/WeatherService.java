package org.isel.mpd.weather;

import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.weather.dto.LocationInfo;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;

import static java.time.LocalDate.now;

public class WeatherService {

    final WeatherApi api;

    public WeatherService(WeatherApi api) {
        this.api = api;
    }

    public WeatherService() {
        this(new WeatherWebApi(new HttpRequest()));
    }

    public Iterable<Location> search(String query) {
        Iterable<LocationInfo> locals = api.search(query);
        return LazyQueries.map(locals, this::toLocation);
    }

    private Location toLocation(LocationInfo l) {
        Iterable<WeatherInfo> wis = () -> api.pastWeather(
            l.getLatitude(),
            l.getLongitude(),
            now().minusDays(30),
            now()).iterator();
        return new Location(
            l.getCountry(),
            l.getRegion(),
            l.getLatitude(),
            l.getLongitude(),
            wis);
    }
}
