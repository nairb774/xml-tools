package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.Match;

public interface InternalMatch extends Match {
    /**
     * @param name
     *            Must not be null.
     * @param value
     *            May be null.
     */
    void add(final String name, final Content value);

    /**
     * @param name
     *            Must not be null.
     * @param value
     *            Must not be null.
     */
    void add(final String name, final Match value);

    /**
     * @param name
     *            Must not be null.
     * @param value
     *            May be null.
     */
    void add(final String name, final String value);

    /**
     * @param match
     *            Must not be null.
     */
    void addAll(Match match);
}
