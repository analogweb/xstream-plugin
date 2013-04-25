package org.analogweb.xstream;

import java.io.IOException;
import java.io.OutputStream;

import org.analogweb.ResponseFormatter;
import org.analogweb.RequestContext;
import org.analogweb.ResponseContext;
import org.analogweb.ResponseContext.ResponseEntity;
import org.analogweb.core.FormatFailureException;

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
public class XStreamXmlFormatter implements ResponseFormatter {

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
    public ResponseEntity formatAndWriteInto(RequestContext response, ResponseContext writeTo,
            String charset, final Object source) {
        return new ResponseEntity() {
            @Override
            public void writeInto(OutputStream responseBody) throws IOException {
                try {
                    getXStream().toXML(source, responseBody);
                } catch (StreamException e) {
                    throw new FormatFailureException(e, source, getClass().getName());
                }
            }
			@Override
			public long getContentLength() {
				return -1;
			}
        };
    }

}
