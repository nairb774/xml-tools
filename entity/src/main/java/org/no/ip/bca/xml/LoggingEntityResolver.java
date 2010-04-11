package org.no.ip.bca.xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LoggingEntityResolver implements EntityResolver {
	private final Logger LOGGER = Logger.getLogger(getClass().getName());
	private Level level = Level.INFO;

	public Level getLevel() {
		return level;
	}

	@Override
	public InputSource resolveEntity(final String publicId,
			final String systemId) throws SAXException, IOException {
		LOGGER.log(level, "Public ID: {0}\nSystem ID: {1}", new Object[] {
				publicId, systemId });
		return null;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}
}
