package org.isel.ipl.mpd.weather.pub;

import java.util.concurrent.Flow.*;

public class TempSubscription implements Subscription {

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription( Subscriber<? super TempInfo> subscriber,
                             String town ) {
        this.subscriber = subscriber;
        this.town = town;
    }
    @Override
    public void request( long n ) {
        for (long i = 0L; i < n; i++) {
            try {
                 subscriber.onNext( TempInfo.fetch( town ) );
            } catch (Exception e) {
                subscriber.onError( e );
                break;
            }
        }
    }
    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}