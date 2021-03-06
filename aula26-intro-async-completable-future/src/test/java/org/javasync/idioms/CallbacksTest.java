/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.javasync.idioms;

import org.junit.Test;

import java.net.URISyntaxException;

import static org.javasync.idioms.Resources.DISCOURSE_ON_THE_METHOD;
import static org.javasync.idioms.Resources.DIVINE_COMEDY;
import static org.javasync.idioms.Resources.METAMORPHOSIS;
import static org.junit.Assert.assertEquals;

public class CallbacksTest {
    @Test public void testCallbacks1ForTwoFiles() {
        int count = Callbacks1.countLines(
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCallbacks2ForTwoFiles() throws InterruptedException, URISyntaxException {
        int count = Callbacks2.countLines(
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
        assertEquals(4745, count);
    }

    @Test public void testCallbacks2ForThreeFiles() throws InterruptedException, URISyntaxException {
        int count = Callbacks2.countLines(
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD,
                        DIVINE_COMEDY);
        assertEquals(10423, count);
    }
    @Test public void testCallbacks3ForTwoFiles() throws InterruptedException, URISyntaxException {
        Callbacks3.countLines(
                        (err, count) -> assertEquals(4745, count.intValue()),
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD);
    }

    @Test public void testCallbacks3ForThreeFiles() throws InterruptedException, URISyntaxException {
        Callbacks3.countLines(
                        (err, count) -> assertEquals(10423, count.intValue()),
                        METAMORPHOSIS,
                        DISCOURSE_ON_THE_METHOD,
                        DIVINE_COMEDY);
    }

}
