package org.no.ip.bca.xml.re;

import org.jdom.Document;
import org.jdom.Element;

public interface XmlMatcher {
	Match doMatch(Document document);

	Match doMatch(Element element);
}
