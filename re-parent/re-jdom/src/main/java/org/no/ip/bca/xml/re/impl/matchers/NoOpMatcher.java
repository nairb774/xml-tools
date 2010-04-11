package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public enum NoOpMatcher implements Matcher {
    INSTANCE;

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        return new StepPoint(null, EmptyMatch.INSTANCE, content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
