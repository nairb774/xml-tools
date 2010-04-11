package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public enum EndMatcher implements Matcher {
    INSTANCE;

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        if (content != null) {
            // We don't want anything
            return null;
        }
        return new StepPoint(null, EmptyMatch.INSTANCE, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
