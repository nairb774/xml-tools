package org.no.ip.bca.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ChainedEntityResolver implements EntityResolver {
	private final List<EntityResolver> resolvers = new ArrayList<EntityResolver>();

	public void addResolver(final EntityResolver resolver) {
		resolvers.add(resolver);
	}

	@Override
	public InputSource resolveEntity(final String publicId,
			final String systemId) throws SAXException, IOException {
		for (final EntityResolver er : resolvers) {
			final InputSource resolveEntity = er.resolveEntity(publicId,
					systemId);
			if (resolveEntity != null) {
				return resolveEntity;
			}
		}
		return null;
	}
}
