package org.no.ip.bca.xml.re.impl.matchers;

import java.util.Map;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Text;

import com.google.common.collect.Maps;

public class SiblingCache {
    private final Map<Content, Content> next = Maps.newHashMap();
    private final Map<Content, Content> prev = Maps.newHashMap();
    private final Content END = new Text(null);

    /**
     * @param content
     *            Must not be null.
     * @return May be null.
     */
    public Content getNext(final Content content) {
        Content n = next.get(content);
        if (n == null) {
            final Element parentElement = content.getParentElement();
            if (parentElement == null) {
                n = END;
            } else {
                final int indexOf = parentElement.indexOf(content);
                final int contentSize = parentElement.getContentSize();
                n = indexOf + 1 == contentSize ? END : parentElement.getContent(indexOf + 1);
            }
            next.put(content, n);
        }
        return n == END ? null : n;
    }

    /**
     * @param content
     *            Must not be null.
     * @return May be null.
     */
    public Content getPrevious(final Content content) {
        Content p = prev.get(content);
        if (p == null) {
            final Element parentElement = content.getParentElement();
            if (parentElement == null) {
                p = END;
            } else {
                final int indexOf = parentElement.indexOf(content);
                p = indexOf == 0 ? END : parentElement.getContent(indexOf - 1);
            }
            prev.put(content, p);
        }
        return p == END ? null : p;
    }
}
