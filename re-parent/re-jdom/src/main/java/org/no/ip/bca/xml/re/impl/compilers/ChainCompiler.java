package org.no.ip.bca.xml.re.impl.compilers;

import java.util.Collection;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.matchers.ChainMatcher;
import org.no.ip.bca.xml.re.impl.matchers.EndMatcher;
import org.no.ip.bca.xml.re.impl.matchers.Matcher;
import org.no.ip.bca.xml.re.impl.matchers.NoOpMatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ChainCompiler {
    private final ICompilerRegistry compilerRegistry;

    /**
     * @param compilerRegistry
     *            Must not be null.
     */
    @Inject
    public ChainCompiler(final ICompilerRegistry compilerRegistry) {
        this.compilerRegistry = compilerRegistry;
    }

    /**
     * @param contents
     *            Must not be null
     * @param ignoreEnd
     * @return Never null.
     */
    public Matcher compile(final Collection<? extends Content> contents, final boolean ignoreEnd) {
        final int size = contents.size();
        if (size == 0) {
            if (ignoreEnd) {
                return NoOpMatcher.INSTANCE;
            }
            return EndMatcher.INSTANCE;
        }
        final Matcher[] matchers = new Matcher[size + (ignoreEnd ? 0 : 1)];
        int i = 0;
        for (final Content content : contents) {
            matchers[i++] = compilerRegistry.compile(content);
        }
        if (!ignoreEnd) {
            matchers[i] = EndMatcher.INSTANCE;
        }
        if (matchers.length == 1) {
            return matchers[0];
        }
        return new ChainMatcher(matchers);
    }
}
