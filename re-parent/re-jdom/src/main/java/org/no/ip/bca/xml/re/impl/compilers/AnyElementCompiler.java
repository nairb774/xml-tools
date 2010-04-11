package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Element;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.AnyElementMatcher;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AnyElementCompiler implements IElementCompiler {
    private final ICompilerRegistry compilerRegistry;
    private final AttributeCompiler attributeCompiler;

    /**
     * @param compilerRegistry
     *            Must not be null.
     * @param attributeCompiler
     *            Must not be null.
     */
    @Inject
    public AnyElementCompiler(final ICompilerRegistry compilerRegistry, final AttributeCompiler attributeCompiler) {
        this.compilerRegistry = compilerRegistry;
        this.attributeCompiler = attributeCompiler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matcher compile(final Element element) {
        final Iterable<AttributeMatcher> attributeMatchers = attributeCompiler.compile(element);
        final Matcher matcher = compilerRegistry.compileChildren(element, true);
        return new AnyElementMatcher(attributeMatchers, matcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI()) && "any".equals(element.getName())) { //$NON-NLS-1$
            return true;
        }
        return false;
    }
}
