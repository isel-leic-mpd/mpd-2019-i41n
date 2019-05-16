package org.isel.mpd.util;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Predicate;

public class SubscriberFilter<T> implements Subscriber<T> {

    public SubscriberFilter(Subscriber<? super T> src, Predicate<T> p) {
    }

    @Override
    public void onSubscribe(Subscription subscription) {

    }

    @Override
    public void onNext(T item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
