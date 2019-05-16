package org.isel.mpd.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubscriptionFromArray<T> implements Subscription {

    private final ExecutorService executor;
    final T[] src;
    final Subscriber<? super T> sub;
    int idx = 0;

    public SubscriptionFromArray(T[] args, Subscriber<? super T> sub) {
        this.src = args;
        this.sub = sub;
        this.executor = Executors.newFixedThreadPool(1);
    }

    @Override
    public void request(long n) {
        executor.submit(() -> {
            for (; idx < n && idx < src.length; idx++) {
                try{
                    sub.onNext(src[idx]);
                } catch(Throwable err) {
                    sub.onError(err);
                }
            }
            if(idx >= src.length){
                sub.onComplete();
                executor.shutdown();
            }
        });
    }

    @Override
    public void cancel() {
        idx = src.length;
        sub.onComplete();
        executor.shutdown();
    }
}
