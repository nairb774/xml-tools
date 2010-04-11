package org.no.ip.bca.xml.re.impl;

import org.jdom.Content;
import org.no.ip.bca.xml.re.Match;

public class StepPoint {
    private final Match match;
    private final Content next;
    private final StepPoint parent;

    /**
     * @param parent
     *            May be null.
     * @param match
     *            Must not be null.
     * @param next
     *            May be null.
     */
    public StepPoint(final StepPoint parent, final Match match, final Content next) {
        this.parent = parent;
        this.match = match;
        this.next = next;
    }

    /**
     * @return Never null.
     */
    public Match getMatch() {
        return match;
    }

    /**
     * @return May be null.
     */
    public Content getNext() {
        return next;
    }

    /**
     * @return May be null.
     */
    public StepPoint getParent() {
        return parent;
    }
}
