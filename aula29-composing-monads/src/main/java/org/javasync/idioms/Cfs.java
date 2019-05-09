package org.javasync.idioms;

import org.javaync.io.AsyncFiles;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Based on chapter
 * 15.2.2 Reactive-style API
 */

public class Cfs {
    public static CompletableFuture<Integer> countLines(String...paths) {
        CompletableFuture[] cfs = Stream
            .of(paths) // Stream<String>
            .map(p ->  // Stream<Cf<Integer>>
                AsyncFiles.readAll(p).thenApply(body -> body.split("\n").length))         //Stream<Cf<Integer>>>
            .toArray(size -> new CompletableFuture[size]);

        return CompletableFuture
            .allOf(cfs)
            .thenApply(__ -> Stream
                .of(cfs)
                .mapToInt(cf -> (int) cf.join())
                .sum());
    }

    /*
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
    */
}
