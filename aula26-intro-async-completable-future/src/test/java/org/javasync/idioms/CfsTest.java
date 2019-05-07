/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.javasync.idioms;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.javasync.idioms.Resources.DISCOURSE_ON_THE_METHOD;
import static org.javasync.idioms.Resources.DIVINE_COMEDY;
import static org.javasync.idioms.Resources.METAMORPHOSIS;
import static org.junit.Assert.assertEquals;

public class CfsTest {
    @Test public void testCf1ForTwoFiles() throws IOException {
        long count = Cf1.countLines(
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCf2ForTwoFiles() throws IOException {
        Cf2.countLines( (err, count) -> assertEquals(4745, count.intValue()),
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
    }

    @Test public void testCf3ForTwoFiles() throws IOException {
        CompletableFuture<Integer> count = Cf3.countLines(METAMORPHOSIS, DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count.join().intValue());
    }

    @Test public void testCf4ForTwoFiles() throws IOException {
        Cf4
            .countLines(METAMORPHOSIS, DISCOURSE_ON_THE_METHOD) // CF<Integer>
            .thenAccept(n -> assertEquals(4745, n.intValue()))  // CF<Void>
            .join();
    }
}
