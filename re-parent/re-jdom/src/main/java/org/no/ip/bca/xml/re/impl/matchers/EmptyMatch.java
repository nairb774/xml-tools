package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.Match;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

public enum EmptyMatch implements Match {
    INSTANCE;

    private final ListMultimap<?, ?> emptyMap = Multimaps.unmodifiableListMultimap(LinkedListMultimap.create());

    @SuppressWarnings("unchecked")
    @Override
    public ListMultimap<String, Content> getElements() {
        return (ListMultimap<String, Content>) emptyMap;
    }

    @Override
    public Content getElementValue(final String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListMultimap<String, Match> getMatches() {
        return (ListMultimap<String, Match>) emptyMap;
    }

    @Override
    public Match getMatchValue(final String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListMultimap<String, String> getStrings() {
        return (ListMultimap<String, String>) emptyMap;
    }

    @Override
    public String getStringValue(final String key) {
        return null;
    }
}
