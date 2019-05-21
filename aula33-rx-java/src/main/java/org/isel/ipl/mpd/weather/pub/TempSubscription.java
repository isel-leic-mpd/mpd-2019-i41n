package org.isel.ipl.mpd.weather.pub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.*;

public class TempSubscription implements Subscription {

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;
    private final ExecutorService exec = Executors.newFixedThreadPool(1);

    public TempSubscription( Subscriber<? super TempInfo> subscriber,
                             String town ) {
        this.subscriber = subscriber;
        this.town = town;
    }
    @Override
    public void request( long n ) {
        Runnable task = () -> {
            for (long i = 0L; i < n; i++) {
                try {
                     subscriber.onNext( TempInfo.fetch( town ) );
                } catch (Exception e) {
                    subscriber.onError( e );
                    break;
                }
            }
        };
        exec.submit(task);
    }
    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}