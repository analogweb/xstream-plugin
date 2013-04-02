package org.analogweb.xstream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.analogweb.ModulesBuilder;
import org.analogweb.core.response.Xml;
import org.junit.Before;
import org.junit.Test;

/**
 * @author snowgoose
 */
public class XStreamPluginModulesConfigTest {

    private XStreamPluginModulesConfig config;
    private ModulesBuilder builder;

    @Before
    public void setUp() throws Exception {
        config = new XStreamPluginModulesConfig();
        builder = mock(ModulesBuilder.class);
    }

    @Test
    public void testPrepare() {
        when(builder.addResponseFormatterClass(Xml.class, XStreamXmlFormatter.class)).thenReturn(
                builder);
        ModulesBuilder actual = config.prepare(builder);
        assertThat(actual, is(builder));
        verify(builder).addResponseFormatterClass(Xml.class, XStreamXmlFormatter.class);
        verify(builder).addRequestValueResolverClass(XStreamXmlValueResolver.class);
    }
}
