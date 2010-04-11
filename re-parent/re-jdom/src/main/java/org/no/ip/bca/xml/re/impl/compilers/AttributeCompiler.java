package org.no.ip.bca.xml.re.impl.compilers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.no.ip.bca.xml.re.XmlCompiler;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.PatternMatcher;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher.NamedAttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher.NamedReAttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher.ReAttributeMatcher;
import org.no.ip.bca.xml.re.impl.matchers.AttributeMatcher.StrictAttributeMatcher;

public class AttributeCompiler {
    /**
     * @param element
     * @return
     */
    @SuppressWarnings("nls")
    public Iterable<AttributeMatcher> compile(final Element element) {
        // This class conveniently ignores the 're:named="name"' attribute which
        // names an element and other re:___attrs.

        @SuppressWarnings("unchecked")
        final List<Attribute> attributes = element.getAttributes();
        if (attributes.isEmpty()) {
            return Collections.emptyList();
        }
        final Map<String, Attribute> reAttributes = new HashMap<String, Attribute>();
        final List<Attribute> actual = new ArrayList<Attribute>();
        for (final Attribute attribute : attributes) {
            if (XmlCompiler.NAMESPACE.equals(attribute.getNamespaceURI())) {
                reAttributes.put(attribute.getName(), attribute);
            } else {
                actual.add(attribute);
            }
        }
        if (actual.isEmpty()) {
            return Collections.emptyList();
        }

        final ArrayList<AttributeMatcher> attrMatchers = new ArrayList<AttributeMatcher>(actual.size());
        for (final Attribute attribute : actual) {
            final Namespace namespace = attribute.getNamespace();
            final String name = attribute.getName();
            final String value = attribute.getValue();

            final String qualifiedName = attribute.getQualifiedName().replace("_", "__").replace(":", "_");
            final Attribute namedAttr = reAttributes.get("named_" + qualifiedName);
            final Attribute namesAttr = reAttributes.get("names_" + qualifiedName);

            AttributeMatcher matcher;
            if (namesAttr != null) {
                final String[] names = namesAttr.getValue().split(" ");
                final Pattern pattern = Pattern.compile(value);
                final PatternMatcher patternMatcher = new PatternMatcher(pattern, names);

                if (namedAttr != null) {
                    matcher = new NamedReAttributeMatcher(namespace, name, patternMatcher, namedAttr.getValue());
                } else {
                    matcher = new ReAttributeMatcher(namespace, name, patternMatcher);
                }
            } else {
                if (namedAttr != null) {
                    matcher = new NamedAttributeMatcher(namespace, name, value, namedAttr.getValue());
                } else {
                    matcher = new StrictAttributeMatcher(namespace, name, value);
                }
            }
            attrMatchers.add(matcher);
        }

        return attrMatchers;
    }
}
