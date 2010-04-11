package org.no.ip.bca.xml.re.impl.matchers;

import java.util.LinkedList;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public class InvertStepPointChain implements Matcher {
    private final Matcher matcher;

    /**
     * @param matcher
     *            Must not be null.
     */
    public InvertStepPointChain(final Matcher matcher) {
        super();
        this.matcher = matcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Content content) {
        StepPoint sp = matcher.match(cache, content);
        if (sp == null) {
            return null;
        }

        final LinkedList<StepPoint> stack = new LinkedList<StepPoint>();
        do {
            stack.addLast(sp);
        } while ((sp = sp.getParent()) != null);

        for (final StepPoint p : stack) {
            sp = new StepPoint(sp, p.getMatch(), p.getNext());
        }
        return sp;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public String toString() {
        return "Invert: " + matcher.toString();
    }
}
