package org.no.ip.bca.xml.re;

import org.jdom.Document;
import org.no.ip.bca.xml.re.impl.DocumentTextNormalizeImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(DocumentTextNormalizeImpl.class)
public interface DocumentTextNormalize {
    /**
     * @param document
     *            Must not be null.
     * @return A copy of the provided document with all "empty" text nodes
     *         removed.
     */
    Document normalize(Document document);
}
