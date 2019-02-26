package org.isel.mpd.weather;
import org.isel.mpd.weather.org.isel.mpd.util.MockRequest;
import org.junit.jupiter.api.Test;
import java.util.List;
import static java.time.LocalDate.parse;
import static org.junit.jupiter.api.Assertions.*;

class WeatherWebApiTest {

    @Test
    void pastWeather() {
        List<WeatherInfo> infos = new WeatherWebApi(new MockRequest())
                .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));

        assertNotNull(infos);
        assertFalse(infos.isEmpty());
        assertTrue(infos.size() > 0);
        WeatherInfo wi = infos.get(0);
        assertEquals(parse("2019-02-01"), wi.date);
        assertEquals(14, wi.tempC);
        assertEquals(13.8, wi.precipMM);
        assertEquals("Patchy rain possible", wi.desc);
    }

    @Test
    void search() {
        List<LocationInfo> infos = new WeatherWebApi(new MockRequest())
                .search("Oporto");
        assertNotNull(infos);
        assertFalse(infos.isEmpty());
        assertTrue(infos.size() > 0);
        LocationInfo li = infos.get(1);
        assertEquals(li.getCountry(), "Portugal");
        assertEquals(li.getRegion(), "Porto");
        assertEquals(li.getLatitude(), 41.15);
        assertEquals(li.getLongitude(), -8.617);
    }

}