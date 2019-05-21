package org.isel.mpd.util.test;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ObservableTest {

    @Test
    public void testObservableOf() {
        Observable<Integer> actual = Observable.just(3, 4, 5, 6, 7);
        actual.subscribe(item -> System.out.println(item));
    }

    @Test
    public void testObservableInterval() {
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
        CompletableFuture<Void> cf = new CompletableFuture<>();
        onePerSec
            .take(7)
            .subscribe(
                item -> System.out.println(item),
                err ->{},
                () -> cf.complete(null));
        cf.join();
    }

    @Test
    public void testObservableIntervalBlocking() {
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
        onePerSec
            .take(7)
            .blockingSubscribe(item -> System.out.println(item));
    }
}
