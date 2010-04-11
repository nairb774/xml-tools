package org.no.ip.bca.xml.re.impl.matchers;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
    private final Pattern pattern;
    private final String[] names;

    /**
     * @param pattern
     *            Must not be null.
     * @param names
     *            Must not be null.
     */
    public PatternMatcher(final Pattern pattern, final String... names) {
        this.pattern = pattern;
        this.names = names;
    }

    /**
     * @param string
     *            Must not be null.
     * @return May be null.
     */
    public InternalMatch match(final String string) {
        final Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) {
            return null;
        }

        final InternalMatch match = new SimpleMatch();
        for (int i = 0; i < names.length; i++) {
            final String name = names[i];
            if (name == null || name.isEmpty()) {
                continue;
            }
            final String value = matcher.group(i);
            match.add(name, value);
        }
        return match;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return pattern.toString() + '~' + Arrays.toString(names);
    }
}
