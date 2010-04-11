package org.no.ip.bca.xml.re.impl.compilers;

import org.jdom.Content;

public class CompileException extends RuntimeException {
    private static final long serialVersionUID = -8634310427874649993L;

    private static String buildMessage(final Content content) {
        return content.toString();
    }

    private static String buildMessage(final Content content, final String message) {
        return content.toString() + " : " + message; //$NON-NLS-1$
    }

    private final Content content;

    /**
     * @param content
     *            Must not be null.
     * @param message
     *            Must not be null.
     */
    public CompileException(final Content content, final String message) {
        super(buildMessage(content, message));
        this.content = content;
    }

    /**
     * @param content
     *            Must not be null.
     * @param cause
     *            Must not be null.
     */
    public CompileException(final Content content, final Throwable cause) {
        super(buildMessage(content), cause);
        this.content = content;
    }

    /**
     * @return Never null.
     */
    public Content getContent() {
        return content;
    }
}
