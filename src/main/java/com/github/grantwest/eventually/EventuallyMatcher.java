package com.github.grantwest.eventually;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class EventuallyMatcher<T> extends TypeSafeMatcher<T> {
    private static Duration defaultTimeout = Duration.ofMillis(5000);
    private final Matcher<T> matcher;
    private final Duration timeout;

    private EventuallyMatcher(Matcher<T> matcher) {
        this(matcher, defaultTimeout);
    }

    private EventuallyMatcher(Matcher<T> matcher, Duration timeout) {
        this.matcher = matcher;
        this.timeout = timeout;
    }

    public static <T> EventuallyMatcher<T> eventually(Matcher<T> matcher) {
        return new EventuallyMatcher<T>(matcher);
    }

    public static <T> EventuallyMatcher<T> eventually(Matcher<T> matcher, Duration timeout) {
        return new EventuallyMatcher<T>(matcher, timeout);
    }


    @Override
    protected boolean matchesSafely(T item) {
        Instant start = Instant.now();
        while(Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
            if(matcher.matches(item)) return true;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(matcher);
    }

    @Override
    public void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText(item.toString());
    }
}
