package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.jdom.Text;
import org.no.ip.bca.xml.re.Match;
import org.no.ip.bca.xml.re.impl.StepPoint;

public abstract class TextMatcher implements Matcher {
    public static class ReTextMatcher extends TextMatcher {
        private final PatternMatcher patternMatcher;

        /**
         * @param patternMatcher
         *            Must not be null.
         */
        public ReTextMatcher(final PatternMatcher patternMatcher) {
            this.patternMatcher = patternMatcher;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Match match(final String textNormalize) {
            return patternMatcher.match(textNormalize);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("nls")
        @Override
        public String toString() {
            return "Text: " + patternMatcher.toString();
        }
    }

    public static class StrictTextMatcher extends TextMatcher {
        private final String string;

        /**
         * @param string
         *            Must not be null.
         */
        public StrictTextMatcher(final String string) {
            this.string = string;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Match match(final String textNormalize) {
            return string.equals(textNormalize) ? EmptyMatch.INSTANCE : null;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("nls")
        @Override
        public String toString() {
            return "Text: " + string;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        if (content != null && content instanceof Text) {
            return match(cache, (Text) content);
        }
        return null;
    }

    /**
     * @param cache
     *            Must not be null.
     * @param text
     *            Must not be null.
     * @return May be null.
     */
    public StepPoint match(final SiblingCache cache, final Text text) {
        final String textNormalize = text.getTextNormalize();
        final Match match = match(textNormalize);
        if (match == null) {
            return null;
        }
        return new StepPoint(null, match, cache.getNext(text));
    }

    /**
     * @param textNormalize
     *            Must not be null.
     * @return May be null
     */
    protected abstract Match match(final String textNormalize);
}
