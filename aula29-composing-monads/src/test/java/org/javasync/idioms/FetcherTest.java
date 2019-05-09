package org.javasync.idioms;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.javasync.idioms.Resources.DISCOURSE_ON_THE_METHOD;
import static org.javasync.idioms.Resources.DIVINE_COMEDY;
import static org.javasync.idioms.Resources.METAMORPHOSIS;
import static org.junit.Assert.assertEquals;

public class FetcherTest {

    @Test
    public void testfetchAndSumBodiesFromPath() throws Exception {
        try(Fetcher fetcher = new Fetcher()) {
            fetcher
                .fetchAndSumBodies(Paths.get("urls"))
                .thenAccept(sum -> assertEquals(527801, sum.intValue()))
                .join();

        }
    }

    @Test
    public void testfetchAndSumBodies() throws Exception {
        try(Fetcher fetcher = new Fetcher()) {
            fetcher
                .fetchAndSumBodies("https://stackoverflow.com/", "https://dzone.com/", "https://github.com")
                .thenAccept(sum -> assertEquals(527801, sum.intValue()))
                .join();

        }
    }
}
