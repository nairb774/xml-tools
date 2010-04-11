package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.no.ip.bca.xml.re.Match;

public abstract class AttributeMatcher {
    public static class NamedAttributeMatcher extends StrictAttributeMatcher {
        private final String matchName;

        /**
         * @param namespace
         *            Must not be null.
         * @param name
         *            Must not be null.
         * @param value
         *            Must not be null.
         * @param matchName
         *            Must not be null.
         */
        public NamedAttributeMatcher(final Namespace namespace, final String name, final String value,
                final String matchName) {
            super(namespace, name, value);
            this.matchName = matchName;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Match match(final Element element) {
            final Attribute attribute = getAttribute(element);
            if (attribute == null) {
                return null;
            }
            final InternalMatch match = new SimpleMatch();
            match.add(matchName, attribute.getValue());
            return match;
        }
    }

    public static class NamedReAttributeMatcher extends ReAttributeMatcher {
        private final String matchName;

        /**
         * @param namespace
         *            Must not be null.
         * @param name
         *            Must not be null.
         * @param patternMatcher
         *            Must not be null.
         * @param matchName
         *            Must not be null.
         */
        public NamedReAttributeMatcher(final Namespace namespace, final String name,
                final PatternMatcher patternMatcher, final String matchName) {
            super(namespace, name, patternMatcher);
            this.matchName = matchName;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Match match(final Element element) {
            final Match match = super.match(element);
            if (match == null) {
                return null;
            }
            final InternalMatch m = new SimpleMatch();
            m.add(matchName, match);
            return m;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("nls")
        @Override
        public String toString() {
            return super.toString() + " name: " + matchName;
        }
    }

    public static class ReAttributeMatcher extends AttributeMatcher {
        private final PatternMatcher patternMatcher;

        /**
         * @param namespace
         *            Must not be null.
         * @param name
         *            Must not be null.
         * @param patternMatcher
         *            Must not be null.
         */
        public ReAttributeMatcher(final Namespace namespace, final String name, final PatternMatcher patternMatcher) {
            super(namespace, name);
            this.patternMatcher = patternMatcher;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Match match(final Element element) {
            final Attribute attribute = getAttribute(element);
            if (attribute == null) {
                return null;
            }
            final String value = attribute.getValue();
            return patternMatcher.match(value);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("nls")
        @Override
        public String toString() {
            return super.toString() + " pattern: " + patternMatcher.toString();
        }
    }

    public static class StrictAttributeMatcher extends AttributeMatcher {
        private final String value;

        /**
         * @param namespace
         *            Must not be null.
         * @param name
         *            Must not be null.
         * @param value
         *            Must not be null.
         */
        public StrictAttributeMatcher(final Namespace namespace, final String name, final String value) {
            super(namespace, name);
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Match match(final Element element) {
            final Attribute attribute = getAttribute(element);
            if (attribute == null) {
                return null;
            }
            final String v = attribute.getValue();
            return value.equals(v) ? EmptyMatch.INSTANCE : null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return super.toString() + '=' + value;
        }
    }

    private final String name;
    private final Namespace namespace;

    /**
     * @param namespace
     *            Must not be null.
     * @param name
     *            Must not be null.
     */
    protected AttributeMatcher(final Namespace namespace, final String name) {
        this.namespace = namespace;
        this.name = name;
    }

    /**
     * @param element
     *            Must not be null.
     * @return May be null.
     */
    protected Attribute getAttribute(final Element element) {
        return element.getAttribute(name, namespace);
    }

    /**
     * @param element
     *            Must not be null.
     * @return May be null.
     */
    public abstract Match match(final Element element);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "[AttributeMatcher " + namespace.getPrefix() + ':' + name;
    }
}
