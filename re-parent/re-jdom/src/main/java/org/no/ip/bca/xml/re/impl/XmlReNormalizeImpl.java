package org.no.ip.bca.xml.re.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.no.ip.bca.xml.re.DocumentTextNormalize;
import org.no.ip.bca.xml.re.XmlCompiler;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.internal.Maps;
import com.google.inject.multibindings.Multibinder;

public class XmlReNormalizeImpl implements XmlReNormalize {
    private interface Handler {
        boolean handle(Element element);
    }

    public static class Mod extends AbstractModule {
        @Override
        protected void configure() {
            final Multibinder<TagHandler> mb = Multibinder.newSetBinder(binder(), TagHandler.class);
            mb.addBinding().to(ReOpt.class);
            mb.addBinding().to(ReOr.class);
            mb.addBinding().to(RePlus.class);
            mb.addBinding().to(ReRepeat.class);
            mb.addBinding().to(ReStar.class);
        }
    }

    private static class ReFollowable implements Handler {
        /**
         * {@inheritDoc}
         */
        public boolean handle(final Element element) {
            if (element.getContentSize() != 0) {
                return false;
            }

            final Element parentElement = element.getParentElement();
            final int index = parentElement.indexOf(element);
            final Content component = parentElement.getContent(index - 1);
            element.addContent(component.detach());

            return true;
        }
    }

    private static class ReOpt extends ReFollowable implements TagHandler {
        private static final String tag = "opt"; //$NON-NLS-1$

        public String getTag() {
            return tag;
        }

        @SuppressWarnings("nls")
        @Override
        public boolean handle(final Element element) {
            super.handle(element);

            element.setName("repeat");
            element.setAttribute("min", "0");
            element.setAttribute("max", "1");

            return true;
        }
    }

    private static class ReOr implements TagHandler {
        private static final String tag = "or"; //$NON-NLS-1$

        public String getTag() {
            return tag;
        }

        @SuppressWarnings("nls")
        @Override
        public boolean handle(final Element element) {
            final Namespace reNamespace = element.getNamespace();
            final Element parentElement = element.getParentElement();
            @SuppressWarnings("unchecked")
            final List<? extends Content> content = parentElement.removeContent();

            final Element $witch = new Element("switch", reNamespace);
            parentElement.addContent($witch);
            Element ca$e = new Element("case", reNamespace);
            $witch.addContent(ca$e);

            for (final Content c : content) {
                if (c instanceof Element) {
                    final Element cElement = (Element) c;
                    if (NAMESPACE.equals(cElement.getNamespaceURI()) && "or".equals(cElement.getName())) {
                        ca$e = new Element("case", reNamespace);
                        $witch.addContent(ca$e);
                        continue;
                    }
                }
                ca$e.addContent(c.detach());
            }

            return true;
        }
    }

    private static class RePlus extends ReFollowable implements TagHandler {
        private static final String tag = "plus"; //$NON-NLS-1$

        public String getTag() {
            return tag;
        }

        @SuppressWarnings("nls")
        @Override
        public boolean handle(final Element element) {
            super.handle(element);

            element.setName("repeat");
            element.setAttribute("min", "1");
            element.setAttribute("max", "unbounded");

            return true;
        }
    }

    private static class ReRepeat extends ReFollowable implements TagHandler {
        private static final String tag = "repeat"; //$NON-NLS-1$

        public String getTag() {
            return tag;
        }

        @SuppressWarnings("nls")
        @Override
        public boolean handle(final Element element) {
            boolean result = super.handle(element);

            final Attribute minAttr = element.getAttribute("min");
            if (minAttr == null) {
                element.setAttribute("min", "0");
                result = true;
            }

            final Attribute maxAttr = element.getAttribute("max");
            if (maxAttr == null) {
                element.setAttribute("max", "unbounded");
                result = true;
            }

            return result;
        }
    }

    private static class ReStar extends ReFollowable implements TagHandler {
        private static final String tag = "star"; //$NON-NLS-1$

        public String getTag() {
            return tag;
        }

        @SuppressWarnings("nls")
        @Override
        public boolean handle(final Element element) {
            super.handle(element);

            element.setName("repeat");
            element.setAttribute("min", "0");
            element.setAttribute("max", "unbounded");

            return true;
        }
    }

    private static class RootHandler implements Handler {
        private final Map<String, TagHandler> reTagHandlers;

        @Inject
        public RootHandler(final Set<TagHandler> reHandlers) {
            final Map<String, TagHandler> handlers = Maps.newHashMap();
            for (final TagHandler handler : reHandlers) {
                handlers.put(handler.getTag(), handler);
            }
            reTagHandlers = handlers;
        }

        public boolean handle(final Element element) {
            if (NAMESPACE.equals(element.getNamespaceURI())) {
                final TagHandler tagHandler = reTagHandlers.get(element.getName());
                if (tagHandler != null && tagHandler.handle(element)) {
                    return true;
                }
            }

            outer: for (;;) {
                @SuppressWarnings("unchecked")
                final List<Element> children = element.getChildren();
                for (final Element child : children) {
                    if (handle(child)) {
                        continue outer;
                    }
                }
                return false;
            }
        }
    }

    private interface TagHandler extends Handler {
        String getTag();
    }

    public static final String NAMESPACE = XmlCompiler.NAMESPACE;
    private final DocumentTextNormalize documentTextNormalize;
    private final RootHandler rootHandler;

    /**
     * @param documentTextNormalize
     *            Must not be null.
     * @param rootHandler
     *            Must not be null.
     */
    @Inject
    public XmlReNormalizeImpl(final DocumentTextNormalize documentTextNormalize, final RootHandler rootHandler) {
        this.rootHandler = rootHandler;
        this.documentTextNormalize = documentTextNormalize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document normalize(final Document document) {
        final Document doc = documentTextNormalize.normalize(document);
        rootHandler.handle(doc.getRootElement());
        return doc;
    }
}
