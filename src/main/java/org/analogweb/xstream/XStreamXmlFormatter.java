package org.analogweb.xstream;

import java.io.IOException;

import org.analogweb.DirectionFormatter;
import org.analogweb.RequestContext;
import org.analogweb.exception.FormatFailureException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * XStreamフレームワークを使用したXMLの
 * レンダリングを行う{@link DirectionFormatter}の実装です。<br/>
 * 既定の実装では、{@link XStream}がXMLを生成する際{@link StaxDriver}
 * を使用するよう設定されます。
 * @author snowgoose
 */
public class XStreamXmlFormatter implements DirectionFormatter {

    private XStream xStream;
    
    protected XStream initXStream() {
        return new XStream(new StaxDriver());
    }
    
    protected XStream getXStream() {
        if (this.xStream == null) {
            this.xStream = initXStream();
        }
        return this.xStream;
    }

    @Override
    public void formatAndWriteInto(RequestContext writeTo, String charset, Object source) {
        try {
            getXStream().toXML(source, writeTo.getResponse().getOutputStream());
        } catch(StreamException e){
            throw new FormatFailureException(e, source, getClass().getName());
        } catch (IOException e) {
            throw new FormatFailureException(e, source, getClass().getName());
        }
    }

}
