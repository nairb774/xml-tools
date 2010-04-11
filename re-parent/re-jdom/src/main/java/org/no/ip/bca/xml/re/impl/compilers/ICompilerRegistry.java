package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Content;
import org.jdom.Element;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;

import com.google.inject.ImplementedBy;

@ImplementedBy(CompilerRegistry.class)
public interface ICompilerRegistry {
    /**
     * @param content
     *            Must not be null.
     * @return Never null.
     */
    Matcher compile(Content content);

    /**
     * @param element
     * @return
     */
    Matcher compileChildren(Element element, boolean ignoreEnd);
}
