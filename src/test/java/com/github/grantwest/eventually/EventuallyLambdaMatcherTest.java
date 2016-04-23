package com.github.grantwest.eventually;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.grantwest.eventually.EventuallyLambdaMatcher.eventuallyEval;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventuallyLambdaMatcherTest {

    @Test
    public void passes() {
        final AtomicInteger i = new AtomicInteger(0);
        assertThat(() -> i.getAndIncrement() == 1, eventuallyEval(is(true)));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fails() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is <true>");
        thrown.expectMessage("but: false");
        assertThat(() -> false, eventuallyEval(is(true), Duration.ofMillis(1)));
    }
}