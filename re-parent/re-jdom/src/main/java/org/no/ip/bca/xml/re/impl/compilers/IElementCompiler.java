package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Element;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;

public interface IElementCompiler {
    /**
     * @param element
     *            Must not be null.
     * @return Never null.
     * @throws CompileException
     *             if the element does not conform.
     */
    Matcher compile(final Element element);

    /**
     * @param element
     * @return
     */
    boolean handles(final Element element);
}
