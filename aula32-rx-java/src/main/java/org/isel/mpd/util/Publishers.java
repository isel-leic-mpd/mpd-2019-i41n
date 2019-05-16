package org.isel.mpd.util;

import java.util.concurrent.Flow.Publisher;
import java.util.function.Function;
import java.util.function.Predicate;

public class Publishers {

    public static <T> Publisher<T> of(T...args){
        return sub ->
            sub.onSubscribe(new SubscriptionFromArray<>(args, sub));
    }

    public static <T,R> Publisher<R> map(Publisher<T> src, Function<T,R> mapper){
        return subR ->  src.subscribe(new SubscriberMapper<>(subR, mapper));
    }

    public static <T> Publisher<T> filter(Publisher<T> src, Predicate<T> p){
        return sub ->  src.subscribe(new SubscriberFilter<>(sub, p));
    }
}
