package com.github.grantwest.eventually;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.grantwest.eventually.EventuallyMatcher.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventuallyMatcherTest {
    private ExecutorService executor;

    @Before
    public void before() {
        executor = Executors.newCachedThreadPool();
    }

    @Test
    public void passes() {
        final Queue<Integer> q = new ConcurrentLinkedQueue<>();
        executor.submit(() -> {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
            q.add(0);
        });

        assertThat(q, eventually(hasItem(0)));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fails() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is <true>");
        thrown.expectMessage("but: false");
        assertThat(false, eventually(is(true), Duration.ofMillis(1)));
    }
}