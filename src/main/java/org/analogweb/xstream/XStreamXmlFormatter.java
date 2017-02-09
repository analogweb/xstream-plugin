package org.analogweb.xstream;

import java.io.IOException;
import java.io.OutputStream;

import org.analogweb.*;
import org.analogweb.core.FormatFailureException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author y2k2mt
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
            public void writeInto(WritableBuffer responseBody) throws IOException {
                try {
                    getXStream().toXML(source, responseBody.asOutputStream());
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
