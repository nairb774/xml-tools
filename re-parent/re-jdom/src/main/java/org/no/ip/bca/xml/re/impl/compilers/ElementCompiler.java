package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Element;
import org.jdom.Namespace;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.ElementMatcher;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ElementCompiler implements IElementCompiler {
    private final ICompilerRegistry compilerRegistry;
    private final AttributeCompiler attributeCompiler;

    /**
     * @param compilerRegistry
     * @param attributeCompiler
     */
    @Inject
    public ElementCompiler(final ICompilerRegistry compilerRegistry, final AttributeCompiler attributeCompiler) {
        this.compilerRegistry = compilerRegistry;
        this.attributeCompiler = attributeCompiler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matcher compile(final Element element) {
        final Namespace namespace = element.getNamespace();
        final String name = element.getName();
        final Iterable<AttributeMatcher> attributeMatchers = attributeCompiler.compile(element);
        final Matcher childrenMatcher = compilerRegistry.compileChildren(element, false);

        return new ElementMatcher(namespace, name, attributeMatchers, childrenMatcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI())) {
            return false;
        }
        return true;
    }
}
