package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletableFuture;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 */

public class Cf4 {
    public static CompletableFuture<Integer> countLines(String p1, String p2) {
        CompletableFuture<Integer> left = new CompletableFuture<>();
        CompletableFuture<Integer> right = new CompletableFuture<>();
        readFile(p1, left);
        readFile(p2, right);
        return left.thenCombine(right, (l, r) -> l + r);
    }

    public static void readFile(
        String path,
        CompletableFuture<Integer> self) {
        AsyncFiles.readAll(path, (err, body)-> {
            if(err != null) {
                self.completeExceptionally(err);
                return;
            }
            self.complete(body.split("\n").length);
        });
    }
}
