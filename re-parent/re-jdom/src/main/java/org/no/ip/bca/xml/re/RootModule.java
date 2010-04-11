package org.no.ip.bca.xml.re;

import org.no.ip.bca.xml.re.impl.XmlReNormalizeImpl;
import org.no.ip.bca.xml.re.impl.compilers.CompilerRegistry;

import com.google.inject.AbstractModule;

public class RootModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new XmlReNormalizeImpl.Mod());
        install(new CompilerRegistry.Mod());
    }
}
