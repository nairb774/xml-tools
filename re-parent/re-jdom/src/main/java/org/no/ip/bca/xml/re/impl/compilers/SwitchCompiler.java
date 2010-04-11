package org.no.ip.bca.xml.re.impl.compilers;

import java.util.List;

import org.jdom.Content;
import org.jdom.Element;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.SwitchMatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SwitchCompiler implements IElementCompiler {
    private final ICompilerRegistry compilerRegistry;

    /**
     * @param compilerRegistry
     *            Must not be null.
     */
    @Inject
    public SwitchCompiler(final ICompilerRegistry compilerRegistry) {
        this.compilerRegistry = compilerRegistry;
    }

    /**
     * {@inheritDoc}
     */
    public Matcher compile(final Element element) {
        @SuppressWarnings("unchecked")
        final List<? extends Content> contents = element.getContent();
        if (contents.isEmpty()) {
            throw new CompileException(element, "Switch statements must have at least one element"); //$NON-NLS-1$
        }

        final Matcher[] matchers = new Matcher[contents.size()];
        int i = 0;
        for (final Content content : contents) {
            if (!(content instanceof Element)) {
                throw new CompileException(content, "Switch statements can only have elements"); //$NON-NLS-1$
            }

            final Element e = (Element) content;
            if (!XmlCompiler.NAMESPACE.equals(e.getNamespaceURI()) || !"case".equals(e.getName())) { //$NON-NLS-1$
                throw new CompileException(e, "Switch statements can only have case elements"); //$NON-NLS-1$
            }

            matchers[i++] = compilerRegistry.compileChildren(e, true);
        }
        return new SwitchMatcher(matchers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI()) && "switch".equals(element.getName())) { //$NON-NLS-1$
            return true;
        }
        return false;
    }
}
