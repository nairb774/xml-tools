package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public class RepeatMatcher implements Matcher {
    private final Matcher matcher;
    private final int max;
    private final int min;

    /**
     * @param matcher
     *            Must not be null.
     * @param min
     *            Must be >= 0.
     * @param max
     *            Must be >= min.
     */
    public RepeatMatcher(final Matcher matcher, final int min, final int max) {
        this.matcher = matcher;
        this.min = min;
        this.max = max;
    }

    private StepPoint doMin(final SiblingCache cache, final Content content) {
        if (min == 0) {
            return new StepPoint(null, EmptyMatch.INSTANCE, content);
        }

        final InternalMatch match = new SimpleMatch();
        Content next = content;
        for (int i = 0; i < min; i++) {
            if (next == null) {
                // Likely did not meet minimum
                // Ex: (a{3,5}) applied to "aa" would result in coming here.
                return null;
            }
            final StepPoint sp = matcher.match(cache, next);
            if (sp == null) {
                return null;
            }
            next = sp.getNext();
            match.addAll(sp.getMatch());
        }
        return new StepPoint(null, match, next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        StepPoint sp = doMin(cache, content);
        if (sp == null) {
            return null;
        }
        for (int i = min; i < max; i++) {
            final Content nextContent = sp.getNext();
            if (nextContent == null) {
                return sp;
            }
            final StepPoint spNext = matcher.match(cache, nextContent);
            if (spNext == null) {
                // Next match did not work - return what we have
                return sp;
            }
            final SimpleMatch match = new SimpleMatch(sp.getMatch());
            match.addAll(spNext.getMatch());
            sp = new StepPoint(sp, match, spNext.getNext());
        }

        return sp;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "Repeat [" + min + ':' + max + "] " + matcher.toString();
    }
}
