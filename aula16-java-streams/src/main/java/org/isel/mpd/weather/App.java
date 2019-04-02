package org.isel.mpd.weather;

import org.isel.mpd.util.queries.LazyQueries;
import org.isel.mpd.util.req.HttpRequest;
import org.isel.mpd.weather.dto.WeatherInfo;

import java.io.IOException;

import static java.lang.System.out;
import static java.time.LocalDate.parse;
import static org.isel.mpd.util.queries.LazyQueries.filter;
import static org.isel.mpd.util.queries.LazyQueries.iterate;
import static org.isel.mpd.util.queries.LazyQueries.map;

public class App {

    public static void main(String[] args) throws IOException {
        Iterable<Integer> seq = LazyQueries.limit(iterate(1, n -> n + 1), 4);
        seq.forEach(System.out::println);
        LazyQueries.toArray(filter(map(seq, App::m), App::f));
        System.out.println();
        LazyQueries.toArray(map(filter(seq, App::f), App::m));
    }

    static int m(int i){
        out.printf("m%d ", i);
        return i;
    }
    static boolean f(int i){
        out.printf("f%d ", i);
        return i % 2== 0;
    }

    static void callWeather() throws IOException {
        WeatherWebApi webApi = new WeatherWebApi(new HttpRequest());
        /*
         * Get past weather at Lisbon between 2019-02-01 and 2019-02-10
         */
        Iterable<WeatherInfo> infos = webApi
            .pastWeather(37.017,-7.933, parse("2019-02-01"), parse("2019-02-28"));
        for (WeatherInfo info : infos)
            out.println(info);
        /*
         * Get locations with name Oporto.
         */
        webApi
            .search("Oporto")
            .forEach(out::println);
    }


}
