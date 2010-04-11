package org.no.ip.bca.xml.re.impl.matchers;

import org.jdom.Content;
import org.no.ip.bca.xml.re.impl.StepPoint;

public interface Matcher {
    /**
     * @param cache
     *            Must not be null.
     * @param content
     *            Can be null.
     * @return <ul>
     *         <li>Failure: null</li>
     *         <li>Success: non-null</li>
     *         </ul>
     */
    StepPoint match(SiblingCache cache, Content content);
}
