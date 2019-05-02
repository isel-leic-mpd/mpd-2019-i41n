package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 */

public class Cf2 {
    public static void countLines(BiConsumer<Throwable, Integer> callback, String p1, String p2) {
        CompletableFuture<Integer> left = new CompletableFuture<>();
        CompletableFuture<Integer> right = new CompletableFuture<>();
        readFile(p1, callback, left, right);
        readFile(p2, callback, right, left);
    }

    public static void readFile(
        String path,
        BiConsumer<Throwable, Integer> callback,
        CompletableFuture<Integer> self,
        CompletableFuture<Integer> other) {
        AsyncFiles.readAll(path, (err, body)-> {
            if(err != null && !other.isDone())
    {
                callback.accept(err, null);
                return;
            }
            self.complete(body.split("\n").length);
            if(other.isDone() && !other.isCompletedExceptionally())
                callback.accept(null, self.join() + other.join());
        });
    }
}
