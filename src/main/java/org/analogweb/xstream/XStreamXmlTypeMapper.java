package org.analogweb.xstream;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.analogweb.Headers;
import org.analogweb.RequestContext;
import org.analogweb.TypeMapper;
import org.analogweb.util.StringUtils;

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
public class XStreamXmlTypeMapper implements TypeMapper {

    private XStream xStream;

    protected XStream initXStream() {
        return new XStream(new StaxDriver());
    }

    @Override
    public Object mapToType(RequestContext context, Object from, Class<?> requiredType,
            String[] formats) {
        if (isXmlType(context)) {
            XStream xStream = getXStream();
            if (InputStream.class.isInstance(from)) {
                return fromXml(xStream, (InputStream) from);
            } else if (Reader.class.isInstance(from)) {
                return fromXml(xStream, (Reader) from);
            }
        }
        return null;
    }

    protected Object fromXml(XStream xStream, InputStream from) {
        try {
            return xStream.fromXML(from);
        } catch (StreamException e) {
            return null;
        }
    }

    protected Object fromXml(XStream xStream, Reader from) {
        try {
            return xStream.fromXML(from);
        } catch (StreamException e) {
            return null;
        }
    }

    protected boolean isXmlType(RequestContext context) {
        Headers headers = context.getRequestHeaders();
        List<String> contentTypes = headers.getValues("Content-Type");
        if (contentTypes == null || contentTypes.isEmpty()) {
            return false;
        }
        String contentType = contentTypes.get(0);
        return StringUtils.isNotEmpty(contentType)
                && (contentType.startsWith("application/xml") || contentType.startsWith("text/xml"));
    }

    protected XStream getXStream() {
        if (this.xStream == null) {
            this.xStream = initXStream();
        }
        return this.xStream;
    }

}
