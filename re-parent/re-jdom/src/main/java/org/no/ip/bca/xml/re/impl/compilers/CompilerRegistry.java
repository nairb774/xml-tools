package org.no.ip.bca.xml.re.impl.compilers;

import java.util.List;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.NoOpMatcher;
import org.no.ip.bca.xml.re.impl.matchers.NamedMatcher.ContentNamedMatcher;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

@Singleton
public class CompilerRegistry implements ICompilerRegistry {
    public static class Mod extends AbstractModule {
        @Override
        protected void configure() {
            final Multibinder<IElementCompiler> mb = Multibinder.newSetBinder(binder(), IElementCompiler.class);
            mb.addBinding().to(AnyElementCompiler.class);
            mb.addBinding().to(ElementCompiler.class);
            mb.addBinding().to(NamedCompiler.class);
            mb.addBinding().to(RepeatCompiler.class);
            mb.addBinding().to(ReTextCompiler.class);
            mb.addBinding().to(SwitchCompiler.class);
        }
    }

    private final Namespace namespace = Namespace.getNamespace("re", XmlCompiler.NAMESPACE); //$NON-NLS-1$
    private final Set<IElementCompiler> elementCompilers;
    private final ChainCompiler chainCompiler;
    private final TextCompiler textCompiler;

    /**
     * @param elementCompilers
     * @param chainCompiler
     * @param textCompiler
     */
    @Inject
    public CompilerRegistry(final Set<IElementCompiler> elementCompilers, final ChainCompiler chainCompiler,
            final TextCompiler textCompiler) {
        this.elementCompilers = elementCompilers;
        this.chainCompiler = chainCompiler;
        this.textCompiler = textCompiler;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public Matcher compile(final Content content) {
        if (content == null) {
            throw new IllegalArgumentException("content is null");
        }
        try {
            if (content instanceof Element) {
                final Element element = (Element) content;
                final IElementCompiler compiler = getCompiler(element);

                final Matcher matcher = compiler.compile(element);
                final String namedValue = element.getAttributeValue("named", namespace);
                return namedValue == null ? matcher : new ContentNamedMatcher(matcher, namedValue);
            } else if (content instanceof Text) {
                return textCompiler.compile((Text) content);
            }
        } catch (final Throwable t) {
            if (t instanceof CompileException) {
                final CompileException compileException = (CompileException) t;
                if (content.equals(compileException.getContent())) {
                    // We do not want to double entries in the stack
                    throw compileException;
                }
            }
            throw new CompileException(content, t);
        }
        throw new CompileException(content, "content not supported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matcher compileChildren(final Element element, final boolean ignoreEnd) {
        final String subValue = element.getAttributeValue("sub", namespace); //$NON-NLS-1$
        final boolean sub = subValue == null ? true : Boolean.parseBoolean(subValue);
        if (sub) {
            @SuppressWarnings("unchecked")
            final List<? extends Content> content = element.getContent();
            return chainCompiler.compile(content, ignoreEnd);
        }
        return NoOpMatcher.INSTANCE;
    }

    /**
     * @param element
     */
    @SuppressWarnings("nls")
    private IElementCompiler getCompiler(final Element element) {
        for (final IElementCompiler compiler : elementCompilers) {
            if (compiler.handles(element)) {
                return compiler;
            }
        }
        throw new CompileException(element, "Cannot find compiler");
    }
}
