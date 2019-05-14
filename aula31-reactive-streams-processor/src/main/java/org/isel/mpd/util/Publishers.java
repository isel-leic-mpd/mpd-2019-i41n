package org.isel.mpd.util;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Function;

public class Publishers {

    public static <T,R> Publisher<R> map(Publisher<T> src, Function<T,R> mapper){
        return subR ->  src.subscribe(new SubscriberT<>(subR, mapper));
    }

    private static class SubscriberT<T, R> implements Subscriber<T> {
        private final Subscriber<? super R> subR;
        private final Function<T,R> mapper;

        public SubscriberT(Subscriber<? super R> subR, Function<T, R> mapper) {
            this.subR = subR;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
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
}
