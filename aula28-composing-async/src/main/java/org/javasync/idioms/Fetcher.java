package org.javasync.idioms;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.javaync.io.AsyncFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Fetcher implements AutoCloseable {
    final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();


    public CompletableFuture<Integer> fetchAndSumBodies(Path folder) throws IOException {
        return Files
            .walk(folder)          // Stream<Path>
            .filter(Files::isRegularFile)     // Stream<Path>
            .map(path -> AsyncFiles.readAll(path)) // Stream<CF<String>>
            .map(cf -> cf.thenCompose(url -> fetchBodyLength(url))) //Stream<Cf<Integer>
            .reduce((prev, curr) -> prev.thenCombine(curr, (l1, l2) -> l1 + l2))
            .get();
    }

    public CompletableFuture<Integer> fetchAndSumBodies(String...urls) {
        return Stream
            .of(urls)
            .map(this::fetchBodyLength)
            .reduce((prev, curr) -> prev.thenCombine(curr, (l1, l2) -> l1 + l2))
            .get();
    }

    private CompletableFuture<Integer> fetchBodyLength(String url) {
        System.out.println(url);
        return asyncHttpClient
            .prepareGet(url)
            .execute()
            .toCompletableFuture()
            .thenApply(Response::getResponseBody)
            .thenApply(body -> body.length());
    }

    @Override
    public void close() throws Exception {
        asyncHttpClient.close();
    }
}
