package org.no.ip.bca.xml.re;

import org.jdom.Document;
import org.no.ip.bca.xml.re.impl.XmlCompilerImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(XmlCompilerImpl.class)
public interface XmlCompiler {
	public static final String NAMESPACE = "bca.no-ip.org/re";

	XmlMatcher compile(Document document);
}
