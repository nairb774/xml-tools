package org.no.ip.bca.xml.re.impl;

import org.jdom.Document;
import org.jdom.Element;
import org.no.ip.bca.xml.re.Match;
import org.no.ip.bca.xml.re.XmlMatcher;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.SiblingCache;

public class XmlMatcherImpl implements XmlMatcher {
    private final Matcher matcher;

    /**
     * @param matcher
     */
    public XmlMatcherImpl(final Matcher matcher) {
        super();
        this.matcher = matcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match doMatch(final Document document) {
        return doMatch(document.getRootElement());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match doMatch(final Element element) {
        final long start = System.nanoTime();
        final SiblingCache siblingCache = new SiblingCache();
        final StepPoint sp = matcher.match(siblingCache, element);
        if (sp == null) {
            return null;
        }
        final Match match = sp.getMatch();
        final long end = System.nanoTime();
        System.out.println("Match time: " + (end - start)); //$NON-NLS-1$
        return match;
    }
}
