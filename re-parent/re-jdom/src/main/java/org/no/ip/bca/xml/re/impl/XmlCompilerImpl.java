package org.no.ip.bca.xml.re.impl;

import org.jdom.Document;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.XmlMatcher;
import org.no.ip.bca.xml.re.impl.compilers.ICompilerRegistry;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;

import com.google.inject.Inject;

public class XmlCompilerImpl implements XmlCompiler {
    private final XmlReNormalize xmlReNormalize;
    private final ICompilerRegistry compilerRegistry;

    /**
     * @param xmlReNormalize
     * @param compilerRegistry
     */
    @Inject
    public XmlCompilerImpl(final XmlReNormalize xmlReNormalize, final ICompilerRegistry compilerRegistry) {
        this.xmlReNormalize = xmlReNormalize;
        this.compilerRegistry = compilerRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XmlMatcher compile(final Document document) {
        final Document doc = xmlReNormalize.normalize(document);
        final Matcher matcher = compilerRegistry.compile(doc.getRootElement());
        return new XmlMatcherImpl(matcher);
    }
}
