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
    @Test public void testCf4ForTwoFiles() throws IOException {
        Cfs
            .countLines(METAMORPHOSIS, DISCOURSE_ON_THE_METHOD, DIVINE_COMEDY) // CF<Integer>
            .thenAccept(n -> assertEquals(10423, n.intValue()))  // CF<Void>
            .join();
    }
}
