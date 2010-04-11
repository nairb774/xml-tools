package org.no.ip.bca.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CachedEntityResolver implements EntityResolver {
    private static Properties loadProperties(final InputStream stream) throws InvalidPropertiesFormatException,
            IOException {
        if (stream == null) {
            throw new IllegalArgumentException("Stream cannot be null"); //$NON-NLS-1$
        }
        final Properties prop = new Properties();
        prop.loadFromXML(stream);
        return prop;
    }

    private final Properties prop;
    private final Logger log = Logger.getLogger(getClass().getName());

    public CachedEntityResolver(final InputStream stream) throws IOException {
        prop = loadProperties(stream);
    }

    public CachedEntityResolver(final String resource) throws IOException {
        final InputStream resourceAsStream = getClass().getResourceAsStream(resource);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Resource not found " + resource + " for class " + getClass().getName()); //$NON-NLS-1$
        }
        try {
            prop = loadProperties(resourceAsStream);
        } finally {
            resourceAsStream.close();
        }
    }

    @Override
    public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
        log.log(Level.FINE, "Looking up {0}", systemId); //$NON-NLS-1$
        final String resource = prop.getProperty(systemId);
        if (resource == null) {
            return null;
        }
        log.log(Level.FINE, "Using {0}", resource); //$NON-NLS-1$

        final InputStream resourceAsStream = getClass().getResourceAsStream(resource);
        if (resourceAsStream == null) {
            log.log(Level.SEVERE, "{0} not found for {1}", new Object[] { resource, systemId }); //$NON-NLS-1$
            return null;
        }
        final InputSource inputSource = new InputSource(resourceAsStream);
        inputSource.setPublicId(publicId);
        inputSource.setSystemId(systemId);
        return inputSource;
    }
}
