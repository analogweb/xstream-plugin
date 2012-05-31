package org.analogweb.xstream;

import java.io.IOException;

import org.analogweb.DirectionFormatter;
import org.analogweb.RequestContext;
import org.analogweb.exception.FormatFailureException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author snowgoose
 */
public class XStreamXmlFormatter implements DirectionFormatter {
    
    private XStream xStream;
    
    public XStreamXmlFormatter(){
        this.xStream = initXStream();
    }

    protected XStream initXStream() {
        return new XStream(new StaxDriver());
    }
    
    protected XStream getXStream(){
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
