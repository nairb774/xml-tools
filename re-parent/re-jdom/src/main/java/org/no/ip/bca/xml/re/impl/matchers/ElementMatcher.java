package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Element;
import org.jdom.Namespace;
import org.no.ip.bca.xml.re.impl.StepPoint;

public class ElementMatcher extends AnyElementMatcher {
    private final String name;
    private final Namespace namespace;

    /**
     * @param namespace
     *            Must not be null.
     * @param name
     *            Must not be null.
     * @param attributeMatchers
     *            Must not be null.
     * @param childMatcher
     *            Must not be null.
     */
    public ElementMatcher(final Namespace namespace, final String name,
            final Iterable<AttributeMatcher> attributeMatchers, final Matcher childMatcher) {
        super(attributeMatchers, childMatcher);
        this.name = name;
        this.namespace = namespace;
    }

    /**
     * @param cache
     *            Must not be null.
     * @param element
     *            Must not be null.
     * @return May be null.
     */
    @Override
    public StepPoint match(final SiblingCache cache, final Element element) {
        if (!namespace.equals(element.getNamespace()) || !name.equals(element.getName())) {
            return null;
        }

        return super.match(cache, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return namespace.getPrefix() + ':' + name;
    }
}
