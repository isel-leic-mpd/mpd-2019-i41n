package org.isel.mpd.util.test;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import static org.isel.mpd.util.Publishers.filter;
import static org.isel.mpd.util.Publishers.map;
import static org.isel.mpd.util.Publishers.of;
import static org.junit.Assert.assertEquals;

public class PublishersTest {
    @Test
    public void testPublisherOf() {
        Integer[] expected = {3, 4, 5, 6, 7};
        Publisher<Integer> actual = of(expected);
        assertPublisherInBulk(expected, actual);
        assertPublisherIndividually(expected, actual);
    }

    @Test
    public void testPublisherMap() {
        String[] src = {"ola", "isel", "super", "mega"};
        Integer[] expected = {3, 4, 5, 4};
        Publisher<Integer> actual = map(of(src), String::length);
        assertPublisherInBulk(expected, actual);
        assertPublisherIndividually(expected, actual);
    }

    @Test
    public void testPublisherFilter() {
        Integer[] src = {5, 34, 532, 65, 6, 56, 365, 34, 1};
        Integer[] expected = {34, 532, 6, 56, 34};
        Publisher<Integer> actual = filter(of(src), nr -> nr % 2 == 0);
        assertPublisherInBulk(expected, actual);
        // assertPublisherIndividually(expected, actual);
    }

    static <T> void assertPublisherInBulk(T[] expected, Publisher<T> actual) {
        CompletableFuture<Void> cf  = new CompletableFuture<>();
        actual
            .subscribe(new Subscriber<T>() {
                int i = 0;
                public void onSubscribe(Subscription subscription) {
                    subscription.request(Long.MAX_VALUE);
                }
                public void onNext(T item) {
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

    static <T> void assertPublisherIndividually(T[] expected, Publisher<T> actual) {
        CompletableFuture<Void> cf  = new CompletableFuture<>();
        actual
            .subscribe(new Subscriber<T>() {
                Subscription sign;
                int i = 0;
                public void onSubscribe(Subscription subscription) {
                    subscription.request(1);
                    this.sign = subscription;
                }
                public void onNext(T item) {
                    assertEquals(expected[i++], item);
                    sign.request(1);
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
