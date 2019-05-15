package org.isel.mpd.util.test;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import static org.isel.mpd.util.Publishers.map;
import static org.isel.mpd.util.Publishers.of;
import static org.junit.Assert.assertEquals;

public class PublishersTest {
    @Test
    public void testPublisherOf() {
        Integer[] expected = {3, 4, 5, 6, 7};
        CompletableFuture<Void> cf  = new CompletableFuture<>();
        of(expected)
            .subscribe(new Subscriber<Integer>() {
                int i = 0;
                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }
                public void onNext(Integer item) {
                    assertEquals(expected[i++], item);
                }
                public void onError(Throwable err) {
                    cf.completeExceptionally(err);
                }
                public void onComplete() {
                    cf.complete(null);
                }
            });
        cf.join();
    }

    @Test
    public void testPublisherMap() {
        String[] src = {"ola", "isel", "super", "mega"};
        int[] expected = {3, 4, 5, 4};
        CompletableFuture<Void> cf  = new CompletableFuture<>();
        map(of(src), String::length)
            .subscribe(new Subscriber<Integer>() {
                int i = 0;
                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }
                public void onNext(Integer item) {
                    assertEquals(expected[i++], item.intValue());
                }
                public void onError(Throwable err) {
                    cf.completeExceptionally(err);
                }
                public void onComplete() {
                    cf.complete(null);
                }
            });
        cf.join();
    }
}
