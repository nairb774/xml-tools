package org.no.ip.bca.xml.re;

import org.jdom.Content;

import com.google.common.collect.ListMultimap;

public interface Match {
    /**
     * @return Never null. May contain null values.
     */
    ListMultimap<String, Content> getElements();

    /**
     * @param key
     *            The key to look up the content with. Must not be null.
     * @return Content object or null if key does not have a content object
     *         associated.
     */
    Content getElementValue(final String key);

    /**
     * @return Never null. Never contains null values.
     */
    ListMultimap<String, Match> getMatches();

    /**
     * @param key
     *            The key to look up the match with. Must not be null.
     * @return Match object or null if key does not have a match object
     *         associated.
     */
    Match getMatchValue(final String key);

    /**
     * @return Never null. May contain null values.
     */
    ListMultimap<String, String> getStrings();

    /**
     * @param key
     *            The key to look up the string with. Must not be null.
     * @return String or null if key does not have a string associated.
     */
    String getStringValue(final String key);
}
