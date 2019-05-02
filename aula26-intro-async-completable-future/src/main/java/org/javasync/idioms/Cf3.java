package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 */

public class Cf3 {
    public static CompletableFuture<Integer> countLines(String p1, String p2) {
        CompletableFuture<Integer> left = new CompletableFuture<>();
        CompletableFuture<Integer> right = new CompletableFuture<>();
        CompletableFuture<Integer> sum = new CompletableFuture<>();
        readFile(p1, sum, left, right);
        readFile(p2, sum, right, left);
        return sum;
    }

    public static void readFile(
        String path,
        CompletableFuture<Integer> sum,
        CompletableFuture<Integer> self,
        CompletableFuture<Integer> other) {
        AsyncFiles.readAll(path, (err, body)-> {
            if(err != null && !other.isDone()) {
                sum.completeExceptionally(err);
                return;
            }
            self.complete(body.split("\n").length);
            if(other.isDone() && !other.isCompletedExceptionally())
                sum.complete(self.join() + other.join());
        });
    }
}
