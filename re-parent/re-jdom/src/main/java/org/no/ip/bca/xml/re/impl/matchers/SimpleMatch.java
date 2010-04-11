package org.no.ip.bca.xml.re.impl.matchers;

import java.util.List;

import org.jdom.Content;
import org.no.ip.bca.xml.re.Match;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

public class SimpleMatch implements InternalMatch {
    private final ListMultimap<String, Content> contents = LinkedListMultimap.create();
    private final ListMultimap<String, Match> matches = LinkedListMultimap.create();
    private final ListMultimap<String, String> strings = LinkedListMultimap.create();

    /**
     * 
     */
    public SimpleMatch() {
    }

    /**
     * @param match
     *            Must not be null.
     */
    public SimpleMatch(final Match match) {
        addAll(match);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final String name, final Content value) {
        contents.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final String name, final Match value) {
        matches.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final String name, final String value) {
        strings.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(final Match match) {
        contents.putAll(match.getElements());
        matches.putAll(match.getMatches());
        strings.putAll(match.getStrings());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMultimap<String, Content> getElements() {
        return contents;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Content getElementValue(final String key) {
        final List<Content> list = contents.get(key);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new IllegalStateException(key + " has too many elements " + list.size()); //$NON-NLS-1$
        }
        return list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMultimap<String, Match> getMatches() {
        return matches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match getMatchValue(final String key) {
        final List<Match> list = matches.get(key);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new IllegalStateException(key + " has too many elements " + list.size()); //$NON-NLS-1$
        }
        return list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListMultimap<String, String> getStrings() {
        return strings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringValue(final String key) {
        final List<String> list = strings.get(key);
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() > 1) {
            throw new IllegalStateException(key + " has too many elements " + list.size()); //$NON-NLS-1$
        }
        return list.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "{matches: " + matches + ", contents: " + contents + ", strings: " + strings + '}';
    }

}
