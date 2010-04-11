package org.no.ip.bca.xml.re.impl.matchers;

import java.util.LinkedList;

import org.jdom.Content;
import org.no.ip.bca.xml.re.Match;
import org.no.ip.bca.xml.re.impl.StepPoint;

public abstract class NamedMatcher implements Matcher {
    public static class ContentNamedMatcher extends NamedMatcher {
        /**
         * @param contentMatcher
         *            Must not be null.
         * @param name
         *            Must not be null.
         */
        public ContentNamedMatcher(final Matcher contentMatcher, final String name) {
            super(contentMatcher, name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Match getMatch(final Content content, final StepPoint stepPoint) {
            final InternalMatch match = new SimpleMatch(stepPoint.getMatch());
            match.add(getName(), content);
            return match;
        }
    }

    public static class MatchNamedMatcher extends NamedMatcher {
        /**
         * @param contentMatcher
         *            Must not be null.
         * @param name
         *            Must not be null.
         */
        public MatchNamedMatcher(final Matcher contentMatcher, final String name) {
            super(contentMatcher, name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Match getMatch(final Content content, final StepPoint stepPoint) {
            final InternalMatch match = new SimpleMatch();
            match.add(getName(), stepPoint.getMatch());
            return match;
        }
    }

    private final Matcher contentMatcher;
    private final String name;

    /**
     * @param contentMatcher
     *            Must not be null.
     * @param name
     *            Must not be null.
     */
    protected NamedMatcher(final Matcher contentMatcher, final String name) {
        this.contentMatcher = contentMatcher;
        this.name = name;
    }

    /**
     * @param content
     *            May be null.
     * @param stepPoint
     *            Must not be null.
     * @return Must not be null.
     */
    protected abstract Match getMatch(Content content, StepPoint stepPoint);

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        StepPoint sp = contentMatcher.match(cache, content);
        if (sp == null) {
            return null;
        }

        final LinkedList<StepPoint> stack = new LinkedList<StepPoint>();
        do {
            stack.addFirst(sp);
        } while ((sp = sp.getParent()) != null);

        for (final StepPoint p : stack) {
            final Match match = getMatch(content, p);
            sp = new StepPoint(sp, match, p.getNext());
        }

        return sp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ' ' + name;
    }
}
