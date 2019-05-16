package org.isel.ipl.mpd.weather.pub;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;

public class TempProcessor implements Processor<TempInfo, TempInfo> {

    private Subscriber<? super TempInfo> subscriber;

    @Override
    public void onNext( TempInfo temp ) {
        subscriber.onNext( new TempInfo( temp.getTown(),
                                       (temp.getTemp() - 32) * 5 / 9) );
    }
    @Override
    public void onSubscribe( Flow.Subscription subscription ) {
        subscriber.onSubscribe( subscription );
    }
    @Override
    public void onError( Throwable throwable ) {
        subscriber.onError( throwable );
    }
    @Override
    public void onComplete() {
        subscriber.onComplete();
    }

    @Override
    public void subscribe(Subscriber<? super TempInfo> subscriber) {
        this.subscriber = subscriber;
    }
}