package org.no.ip.bca.xml.re.impl;

import java.util.Iterator;

import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.no.ip.bca.xml.re.DocumentTextNormalize;

public class DocumentTextNormalizeImpl implements DocumentTextNormalize {
    @Override
    public Document normalize(Document document) {
        document = (Document) document.clone();
        for (@SuppressWarnings("unchecked")
        final Iterator<Content> iter = document.getDescendants(); iter.hasNext();) {
            final Content next = iter.next();
            if (next instanceof Text) {
                final Text t = (Text) next;
                if (t.getTextNormalize().isEmpty()) {
                    iter.remove();
                }
            } else if (next instanceof Comment || next instanceof ProcessingInstruction) {
                iter.remove();
            }
        }
        return document;
    }
}
