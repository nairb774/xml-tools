package org.no.ip.bca.xml.re.impl.matchers;

import java.util.Arrays;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public class SwitchMatcher implements Matcher {
    private final Matcher[] matchers;

    /**
     * @param matchers
     *            Must not be null or empty
     */
    public SwitchMatcher(final Matcher... matchers) {
        this.matchers = new Matcher[matchers.length];
        for (int i = 0, j = matchers.length - 1; i < matchers.length; i++, j--) {
            this.matchers[i] = matchers[j];
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        StepPoint sp = null;
        for (final Matcher matcher : matchers) {
            final StepPoint match = matcher.match(cache, content);
            if (match == null) {
                continue;
            }
            sp = new StepPoint(sp, match.getMatch(), match.getNext());
        }
        return sp;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "Switch: " + Arrays.toString(matchers);
    }
}
