package org.isel.mpd.util;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Predicate;

public class SubscriberFilter<T> implements Subscriber<T> {

    final Subscriber<? super T> sub;
    final Predicate<T> p;
    long req = 0;
    Subscription subscription;

    public SubscriberFilter(Subscriber<? super T> sub, Predicate<T> p) {
        this.sub = sub;
        this.p = p;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        sub.onSubscribe(new Subscription() {
            public void request(long n) {
                req += n;
                SubscriberFilter.this.subscription = subscription;
                subscription.request(1);
            }
            public void cancel() { sub.onComplete(); }
        });
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }

    @Override
    public void onNext(T item) {
        if(p.test(item)) {
            if(req >= 0) {
                sub.onNext(item);
            }
        }
        if(req > 0)
            this.subscription.request(1);
    }
}
