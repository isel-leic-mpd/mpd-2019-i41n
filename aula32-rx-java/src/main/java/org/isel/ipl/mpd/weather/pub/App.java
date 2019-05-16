package org.isel.ipl.mpd.weather.pub;

import org.isel.ipl.mpd.weather.sub.TempSubscriber;
import org.isel.mpd.util.Publishers;

import java.util.concurrent.Flow.*;

public class App {

    public static void main( String[] args ) {
        getTemperatures( "New York" )
            .subscribe( new TempSubscriber() );

        // getCelsiusTemperatures( "New York" ).subscribe( new TempSubscriber() );

        Publishers
            .map(getTemperatures( "London"), App::fromFtoCelsius)
            .subscribe( new TempSubscriber() );
    }

    private static TempInfo fromFtoCelsius(TempInfo temp) {
        return new TempInfo( temp.getTown(), (temp.getTemp() - 32) * 5 / 9);
    }

    private static Publisher<TempInfo> getTemperatures(String town ) {
        return sub -> sub.onSubscribe(
                                new TempSubscription( sub, town ) );
    }

    private static Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return sub -> {
            TempProcessor processor = new TempProcessor(); // -> Celsius
            processor.subscribe(sub);
            processor.onSubscribe(new TempSubscription(processor, town));// -> fahrenheits
        };
    }
}