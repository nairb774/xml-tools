package org.no.ip.bca.xml.re.impl;

import org.jdom.Document;

import com.google.inject.ImplementedBy;

@ImplementedBy(XmlReNormalizeImpl.class)
public interface XmlReNormalize {
	Document normalize(Document document);
}
