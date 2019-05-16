package org.isel.mpd.util;

import java.util.concurrent.Flow;
import java.util.function.Function;

public class SubscriberMapper<T, R> implements Flow.Subscriber<T> {
    private final Flow.Subscriber<? super R> subR;
    private final Function<T,R> mapper;

    public SubscriberMapper(Flow.Subscriber<? super R> subR, Function<T, R> mapper) {
        this.subR = subR;
        this.mapper = mapper;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subR.onSubscribe(subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subR.onError(throwable);
    }

    @Override
    public void onComplete() {
        subR.onComplete();
    }

    @Override
    public void onNext(T item) {
        subR.onNext(mapper.apply(item));
    }
}