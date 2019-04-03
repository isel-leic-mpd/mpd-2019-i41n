package org.isel.util;

import org.isel.mpd.weather.WeatherRestfullApi;
import org.isel.mpd.weather.WeatherService;
import org.isel.mpd.weather.dto.WeatherInfo;
import org.junit.Test;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SpliteratorTest {
    @Test
    public void externalIterator() {
    WeatherService service = new WeatherService(new WeatherRestfullApi());
        Iterable<WeatherInfo> weathers = () -> service
            .search("oporto")
            .findFirst()
            .get()
            .getPast30daysWeather()
            .iterator();
        // for(WeatherInfo item : weathers){}
        // <=>
        int count = 0;
        for(Iterator<WeatherInfo> iter = weathers.iterator(); iter.hasNext();){
            WeatherInfo item = iter.next();
            count++;
            if(item.getTempC() > 20)
                break;
        }
        assertEquals(13, count);
    }

    @Test
    public void internalIterator() {
    WeatherService service = new WeatherService(new WeatherRestfullApi());
        Stream<WeatherInfo> weathers = service
            .search("oporto")
            .findFirst()
            .get()
            .getPast30daysWeather();
        Spliterator<WeatherInfo> iter = weathers.spliterator();
        int[] count = {0};
        boolean[] proceed = {true};
        while(proceed[0] && iter.tryAdvance(item -> {
            count[0]++;
            if(item.getTempC() > 20)
                proceed[0] = false;
        })){}
        assertEquals(13, count[0]);
    }
}
