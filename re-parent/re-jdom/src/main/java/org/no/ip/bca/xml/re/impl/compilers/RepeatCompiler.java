package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Element;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.InvertStepPointChain;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.RepeatMatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RepeatCompiler implements IElementCompiler {
    /**
     * @param element
     *            The element to read the attribute from.
     * @param attributeName
     *            The attribute to read and parse.
     * @return The integer returned will be non-negitive.
     */
    @SuppressWarnings("nls")
    private static boolean parseBoolean(final Element element, final String attributeName, final boolean default_) {
        final String value = element.getAttributeValue(attributeName);
        if (value == null) {
            return default_;
        }
        return Boolean.parseBoolean(value);
    }

    /**
     * @param element
     *            The element to read the attribute from.
     * @param attributeName
     *            The attribute to read and parse.
     * @return The integer returned will be non-negitive.
     */
    @SuppressWarnings("nls")
    private static int parseInt(final Element element, final String attributeName, final int default_) {
        final String value = element.getAttributeValue(attributeName);
        if (value == null) {
            return default_;
        }
        if ("unbounded".equals(value)) {
            return Integer.MAX_VALUE;
        }
        try {
            final int v = Integer.parseInt(value);
            if (v < 0) {
                throw new CompileException(element, "Attribute " + attributeName + " must be non-negitive, it was "
                        + value);
            }
            return v;
        } catch (final NumberFormatException e) {
            throw new CompileException(element, value + " not parseable");
        }
    }

    private final ICompilerRegistry compilerRegistry;

    /**
     * @param chainCompiler
     */
    @Inject
    public RepeatCompiler(final ICompilerRegistry compilerRegistry) {
        this.compilerRegistry = compilerRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public Matcher compile(final Element element) {
        final int min = parseInt(element, "min", 0);
        final int max = parseInt(element, "max", Integer.MAX_VALUE);
        if (min > max) {
            throw new CompileException(element, "min must be <= max");
        }
        final Matcher childrenMatcher = compilerRegistry.compileChildren(element, true);
        final RepeatMatcher matcher = new RepeatMatcher(childrenMatcher, min, max);

        final boolean reluctant = parseBoolean(element, "reluctant", false);
        return reluctant ? new InvertStepPointChain(matcher) : matcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI()) && "repeat".equals(element.getName())) {
            return true;
        }
        return false;
    }

}
