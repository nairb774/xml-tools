package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Element;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.NamedMatcher.MatchNamedMatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NamedCompiler implements IElementCompiler {
    private final ICompilerRegistry compilerRegistry;

    /**
     * @param compilerRegistry
     */
    @Inject
    public NamedCompiler(final ICompilerRegistry compilerRegistry) {
        this.compilerRegistry = compilerRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public Matcher compile(final Element element) {
        final String name = element.getAttributeValue("name");
        if (name == null) {
            throw new CompileException(element, "must have 'name' attribute");
        }
        final Matcher compileChildren = compilerRegistry.compileChildren(element, true);

        return new MatchNamedMatcher(compileChildren, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI()) && "named".equals(element.getName())) { //$NON-NLS-1$
            return true;
        }
        return false;
    }
}
