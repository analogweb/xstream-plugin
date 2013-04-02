package org.analogweb.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.analogweb.InvocationMetadata;
import org.analogweb.MediaType;
import org.analogweb.RequestContext;
import org.analogweb.TypeMapper;
import org.analogweb.core.MediaTypes;
import org.analogweb.core.SpecificMediaTypeRequestValueResolver;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * XStreamによる変換により、リクエストされたXMLを任意のオブジェクト
 * のインスタンスに変換する{@link TypeMapper}の実装です。<br/>
 * 変換元の値として、リクエストされたXMLを保持する{@link InputStream}
 * または{@link Reader}(読み込み可能なリクエストボディ)が指定されている
 * 必要があります。
 * @author snowgoose
 */
public class XStreamXmlValueResolver implements SpecificMediaTypeRequestValueResolver {

    private XStream xStream;

    protected XStream initXStream() {
        return new XStream(new StaxDriver());
    }

    @Override
    public Object resolveValue(RequestContext context, InvocationMetadata metadata, String key,
            Class<?> requiredType) {
        XStream xStream = getXStream();
        try {
            return fromXml(xStream, context.getRequestBody());
        } catch (IOException e) {
            return null;
        }
    }

    protected Object fromXml(XStream xStream, InputStream from) {
        try {
            return xStream.fromXML(from);
        } catch (StreamException e) {
            return null;
        }
    }

    @Override
    public boolean supports(MediaType mediaType) {
        return MediaTypes.valueOf("*/xml").isCompatible(mediaType)
                || mediaType.getSubType().endsWith("+xml");
    }

    protected XStream getXStream() {
        if (this.xStream == null) {
            this.xStream = initXStream();
        }
        return this.xStream;
    }
}
