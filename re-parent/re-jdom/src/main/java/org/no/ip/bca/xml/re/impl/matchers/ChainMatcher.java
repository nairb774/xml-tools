package org.no.ip.bca.xml.re.impl.matchers;

import java.util.Arrays;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

/**
 * This is a greedy implementation. Use {@link InvertStepPointChain} to make
 * reluctant.
 * 
 * @author Brian Atkinson
 */
public class ChainMatcher implements Matcher {
    private final Matcher[] matchers;

    /**
     * @param matchers
     *            Must not be null and have more then one
     */
    public ChainMatcher(final Matcher... matchers) {
        this.matchers = matchers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        final StepPoint first = matchers[0].match(cache, content);
        if (first == null) {
            return null;
        }
        // We delay the creation of the array to save allocations if the first
        // fails.
        final StepPoint[] points = new StepPoint[matchers.length];
        points[0] = first;
        for (int i = 1; i < matchers.length; i++) {
            final Content prevStepPointNext = points[i - 1].getNext();
            points[i] = matchers[i].match(cache, prevStepPointNext);
            if (points[i] != null) {
                continue;
            }
            // No match -> try to unroll and retry
            while (i > 0 && points[i] == null) {
                i--;
                points[i] = points[i].getParent();
            }
            // Coming out of this loop we want to leave 'i' on the last done
            // because the for loop will auto increment to
            // the next attempt
            if (i == 0 && points[i] == null) {
                // Reached the beginning with nothing to unroll -> no match.
                return null;
            }
        }
        final InternalMatch match = new SimpleMatch();
        for (final StepPoint point : points) {
            match.addAll(point.getMatch());
        }
        final StepPoint point = points[points.length - 1];
        return new StepPoint(null, match, point.getNext());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Arrays.toString(matchers);
    }
}
