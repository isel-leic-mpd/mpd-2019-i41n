package org.isel.ipl.mpd.weather.pub;

import org.isel.ipl.mpd.weather.sub.TempSubscriber;

import java.util.concurrent.Flow.*;

public class App {

    public static void main( String[] args ) {
        getTemperatures( "New York" ).subscribe( new TempSubscriber() );

    }
    private static Publisher<TempInfo> getTemperatures(String town ) {
        return sub -> sub.onSubscribe(
                                new TempSubscription( sub, town ) );
    }
}