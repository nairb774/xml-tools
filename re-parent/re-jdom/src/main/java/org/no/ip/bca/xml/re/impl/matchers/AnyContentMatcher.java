package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public enum AnyContentMatcher implements Matcher {
    INSTANCE;

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        if (content == null) {
            return null;
        }

        final Content next = cache.getNext(content);
        return new StepPoint(null, EmptyMatch.INSTANCE, next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
