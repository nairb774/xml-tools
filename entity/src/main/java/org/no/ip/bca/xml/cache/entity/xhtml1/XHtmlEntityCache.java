package org.no.ip.bca.xml.cache.entity.xhtml1;

import java.io.IOException;

import org.no.ip.bca.xml.CachedEntityResolver;

public class XHtmlEntityCache extends CachedEntityResolver {
    public XHtmlEntityCache() throws IOException {
        super("xhtml.properties"); //$NON-NLS-1$
    }
}
