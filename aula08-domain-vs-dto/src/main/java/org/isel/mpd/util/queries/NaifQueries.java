package org.isel.mpd.util.queries;

import org.isel.mpd.weather.dto.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class NaifQueries {

    /**
     * V1 -- hard coded
     */
    public static List<WeatherInfo> filterSunnyDays(List<WeatherInfo> infos){
        List<WeatherInfo> res = new ArrayList<>();
        for (WeatherInfo wi : infos) {
            if(wi.getDesc().equals("Sunny"))
                res.add(wi);
        }
        return res;
    }

    /**
     * V2 -- description value by parameter
     */
    public static List<WeatherInfo> filterByDesc(List<WeatherInfo> infos, String desc){
        List<WeatherInfo> res = new ArrayList<>();
        for (WeatherInfo wi : infos) {
            if(wi.getDesc().equals(desc))
                res.add(wi);
        }
        return res;
    }

    /**
     * V3 -- Behavior Parametrization
     */
    public static List<WeatherInfo> filterWeather(Iterable<WeatherInfo> infos, WeatherPredicate pred){
        List<WeatherInfo> res = new ArrayList<>();
        for (WeatherInfo wi : infos) {
            if(pred.test(wi))
                res.add(wi);
        }
        return res;
    }
}
