package org.javasync.idioms;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * Based on chapter
 * 15.2 Synchronous and asynchronous APIs
 *
 */
public class Threads2 {

    public static long countLines(String...paths) {
        AtomicLong total = new AtomicLong(0);
        Stream<Thread> tasks = Stream
            .of(paths)
            .map(path -> new Thread(() -> total.addAndGet(nrOfLines(path))))
            .peek(Thread::start)
            .peek(__ -> System.out.println("Thread started"));
            // .collect(toList()); // !!!! NECESSÁRIO para executar em PARALELO!!!

        tasks.forEach(t -> {
            try {
                t.join();
                System.out.println("Thread finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return total.get();
    }

    private static long nrOfLines(String path) {
        try {
            Path p = Paths.get(path);
            return Files.lines(p).count();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}