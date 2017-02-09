package org.analogweb.xstream;

import org.analogweb.ModulesBuilder;
import org.analogweb.PluginModulesConfig;
import org.analogweb.core.response.Xml;
import org.analogweb.util.MessageResource;
import org.analogweb.util.PropertyResourceBundleMessageResource;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

/**
 * Integrating <a href="http://xstream.codehaus.org/">XStream</a> to
 * <a href="https://github.com/analogweb">Analog Web Framework</a>
 * This plugin processing {@link Xml} with XStream.
 * @author y2k2mt
 */
public class XStreamPluginModulesConfig implements PluginModulesConfig {

    public static final MessageResource PLUGIN_MESSAGE_RESOURCE = new PropertyResourceBundleMessageResource(
            "org.analogweb.xstream.analog-messages");
    private static final Log log = Logs.getLog(XStreamPluginModulesConfig.class);

    @Override
    public ModulesBuilder prepare(ModulesBuilder builder) {
        log.log(PLUGIN_MESSAGE_RESOURCE, "IXSB000001");
        builder.addResponseFormatterClass(Xml.class, XStreamXmlFormatter.class);
        builder.addRequestValueResolverClass(XStreamXmlValueResolver.class);
        return builder;
    }
}
