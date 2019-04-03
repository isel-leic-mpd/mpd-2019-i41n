package org.isel.mpd.weather;

import org.isel.mpd.util.StreamUtil;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.util.req.Request;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.isel.mpd.weather.model.Location;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertEquals;

public class WeatherServiceTest {
    @Test
    public void testPastWeatherInOporto(){
        // CountableRequest req = new CountableRequest(new HttpRequest());
        Request r = new HttpRequest();

        int[] count = {0};
        Function<String, String> counter = path -> {
            count[0]++;
            System.out.println("Request " + path);
            return path;
        };
        Function<String, Stream<String>> req = counter.andThen(r::getLines);

        WeatherService service = new WeatherService(new WeatherRestfullApi(req::apply));
        Location[] locals = service.search("oporto").toArray(size -> new Location[size]);
        assertEquals(1, count[0]);

        Stream<WeatherInfo> oportoWeather = of(locals).skip(1).findFirst().get().getPast30daysWeather();
        int tempC = oportoWeather
            .map(WeatherInfo::getTempC)
            .max(Integer::compareTo)
            .get();

        assertEquals(25, tempC);
        assertEquals(2, count[0]);

        of(locals).count(); // + 6 requests of pastWeather for 6 locations
        assertEquals(2, count[0]);
    }


    @Test
    public void testDayWithMaxTemperatureInOporto(){
        WeatherService service = new WeatherService(new WeatherRestfullApi());
        Stream<Location> locals = service.search("oporto");
        WeatherInfo[] oportoWeather = locals
            .skip(1)
            .findFirst()
            .get()
            .getPast30daysWeather()
            .toArray(size -> new WeatherInfo[size]);

        // <=> WeatherInfo maxTemp = max(oportoWeather, (w1, w2) -> w1.getTempC() - w2.getTempC());
        WeatherInfo maxTemp = of(oportoWeather).max(comparing(WeatherInfo::getTempC)).get();

        WeatherInfo maxDesc = of(oportoWeather).max(comparing(WeatherInfo::getDesc)).get();
        assertEquals(18, maxDesc.getTempC());

        WeatherInfo maxDescAndTemp = of(oportoWeather).max(
            comparing(WeatherInfo::getDesc).thenComparing(WeatherInfo::getTempC)).get();
        assertEquals(18, maxDescAndTemp.getTempC());

        Comparator<WeatherInfo> cmp = comparing(WeatherInfo::getDesc)
            .thenComparing(WeatherInfo::getTempC)
            .thenComparing(WeatherInfo::getPrecipMM)
            .thenComparing(WeatherInfo::getDate);
    }

    @Test
    public void testCollapseTemperatures() {
        WeatherService service = new WeatherService(new WeatherRestfullApi());
        Stream<Location> locals = service.search("oporto");
        Stream<Integer> temps = locals
            .skip(1)
            .findFirst()
            .get()
            .getPast30daysWeather()
            .map(WeatherInfo::getTempC);

        temps = StreamUtil.collapse(temps);

        String res = temps
            .map(Object::toString)
            .collect(Collectors.joining(","));

        String expected = "16,13,14,12,11,14,19,16,18,15,14,17,21,19,14,17,19,21,22,20,22,23,21,24,25,21,20,18";
        assertEquals(expected, res);
    }
}
