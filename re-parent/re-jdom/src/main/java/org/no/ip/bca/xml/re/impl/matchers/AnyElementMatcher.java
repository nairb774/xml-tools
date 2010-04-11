package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.jdom.Element;
import org.no.ip.bca.xml.re.Match;
import org.no.ip.bca.xml.re.impl.StepPoint;

public class AnyElementMatcher implements Matcher {
    private final Iterable<AttributeMatcher> attributeMatchers;
    private final Matcher childMatcher;

    /**
     * @param attributeMatchers
     *            Must not be null.
     * @param childMatcher
     *            Must not be null.
     */
    public AnyElementMatcher(final Iterable<AttributeMatcher> attributeMatchers, final Matcher childMatcher) {
        this.attributeMatchers = attributeMatchers;
        this.childMatcher = childMatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        if (content != null && content instanceof Element) {
            return match(cache, (Element) content);
        }
        return null;
    }

    /**
     * @param cache
     *            Must not be null.
     * @param element
     *            Must not be null.
     * @return May be null.
     */
    public StepPoint match(final SiblingCache cache, final Element element) {
        final InternalMatch match = new SimpleMatch();
        for (final AttributeMatcher attributeMatcher : attributeMatchers) {
            final Match m = attributeMatcher.match(element);
            if (m == null) {
                return null;
            }
            match.addAll(m);
        }

        final Content content = element.getContentSize() == 0 ? null : element.getContent(0);
        final StepPoint sp = childMatcher.match(cache, content);
        if (sp == null) {
            return null;
        }
        match.addAll(sp.getMatch());

        return new StepPoint(null, match, cache.getNext(element));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
