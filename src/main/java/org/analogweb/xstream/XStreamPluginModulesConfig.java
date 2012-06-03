package org.analogweb.xstream;

import org.analogweb.ModulesBuilder;
import org.analogweb.PluginModulesConfig;
import org.analogweb.core.direction.Xml;
import org.analogweb.util.MessageResource;
import org.analogweb.util.PropertyResourceBundleMessageResource;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

/**
 * @author snowgoose
 */
public class XStreamPluginModulesConfig implements PluginModulesConfig {

    /**
     * XStreamプラグインで使用する{@link MessageResource}です。
     */
    public static final MessageResource PLUGIN_MESSAGE_RESOURCE = new PropertyResourceBundleMessageResource(
            "org.analogweb.xstream.analog-messages");
    private static final Log log = Logs.getLog(XStreamPluginModulesConfig.class);

    @Override
    public ModulesBuilder prepare(ModulesBuilder builder) {
        log.log(PLUGIN_MESSAGE_RESOURCE, "IXSB000001");
        builder.addDirectionFormatterClass(Xml.class, XStreamXmlFormatter.class);
        return builder;
    }

}
