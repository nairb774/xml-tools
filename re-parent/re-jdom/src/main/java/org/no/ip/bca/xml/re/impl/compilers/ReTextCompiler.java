package org.no.ip.bca.xml.re.impl.compilers;

import java.util.regex.Pattern;

import org.jdom.Element;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.PatternMatcher;
import org.no.ip.bca.xml.re.impl.matchers.TextMatcher;

import com.google.inject.Singleton;

@Singleton
public class ReTextCompiler implements IElementCompiler {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("nls")
    @Override
    public Matcher compile(final Element element) {
        final String textNormalize = element.getTextNormalize();
        final Pattern pattern = Pattern.compile(textNormalize);
        final String names = element.getAttributeValue("names");
        final PatternMatcher patternMatcher = names == null ? new PatternMatcher(pattern) : new PatternMatcher(pattern,
                names.split(" "));
        return new TextMatcher.ReTextMatcher(patternMatcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handles(final Element element) {
        if (XmlCompiler.NAMESPACE.equals(element.getNamespaceURI()) && "re".equals(element.getName())) { //$NON-NLS-1$
            return true;
        }
        return false;
    }
}
