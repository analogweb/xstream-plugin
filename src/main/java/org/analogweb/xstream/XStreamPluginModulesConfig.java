package org.analogweb.xstream;

import org.analogweb.ModulesBuilder;
import org.analogweb.PluginModulesConfig;
import org.analogweb.core.response.Xml;
import org.analogweb.util.MessageResource;
import org.analogweb.util.PropertyResourceBundleMessageResource;
import org.analogweb.util.logging.Log;
import org.analogweb.util.logging.Logs;

/**
 * <a href="http://xstream.codehaus.org/">XStream</a>フレームワークを
 * <a href="https://github.com/analogweb">Analog Web Framework</a>
 * に統合する{@link PluginModulesConfig}です。<br/>
 * このプラグインを使用することで、{@link Xml}使用時に、
 * XStreamを利用したXMLの生成とレスポンスを行う事が可能になります。
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
        builder.addResponseFormatterClass(Xml.class, XStreamXmlFormatter.class);
        builder.addAttributesHandlerClass(XStreamXmlTypeMapper.class);
        return builder;
    }

}
