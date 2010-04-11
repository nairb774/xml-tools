package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Text;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.TextMatcher;

public class TextCompiler {
    /**
     * @param text
     * @return
     */
    public Matcher compile(final Text text) {
        final String textNormalize = text.getTextNormalize();
        return new TextMatcher.StrictTextMatcher(textNormalize);
    }
}
