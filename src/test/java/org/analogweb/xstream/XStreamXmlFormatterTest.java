package org.analogweb.xstream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.analogweb.RequestContext;
import org.analogweb.ResponseContext;
import org.analogweb.WritableBuffer;
import org.analogweb.core.DefaultWritableBuffer;
import org.analogweb.core.FormatFailureException;
import org.analogweb.xstream.model.Foo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

/**
 * @author y2k2mt
 */
public class XStreamXmlFormatterTest {

    private XStreamXmlFormatter formatter;
    private RequestContext context;
    private ResponseContext responseContext;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        formatter = new XStreamXmlFormatter();
        context = mock(RequestContext.class);
        responseContext = mock(ResponseContext.class);
    }

    @Test
    public void testFormatAndWriteInto() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        WritableBuffer buffer = DefaultWritableBuffer.writeBuffer(out);
        Foo f = new Foo();
        // TODO 
        //        f.setBirthDay(new SimpleDateFormat("yyyyMMdd").parse("19780420"));
        formatter.formatAndWriteInto(context, responseContext, "UTF-8", f).writeInto(buffer);
        String actual = new String(out.toByteArray());
        assertThat(
                actual,
                is("<?xml version=\"1.0\" ?><org.analogweb.xstream.model.Foo><name>foo</name><age>34</age></org.analogweb.xstream.model.Foo>"));
    }

    @Test
    public void testFormatAndWriteIntoRaiseStreamException() throws Exception {
        thrown.expect(FormatFailureException.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        WritableBuffer buffer = DefaultWritableBuffer.writeBuffer(out);
        formatter = new XStreamXmlFormatter() {
            @Override
            protected XStream initXStream() {
                return new XStream() {
                    @Override
                    public void toXML(Object source, OutputStream out) throws StreamException {
                        throw new StreamException("streaming error!");
                    }
                };
            }

        };
        Foo f = new Foo();
        f.setBirthDay(new SimpleDateFormat("yyyyMMdd").parse("19780420"));
        formatter.formatAndWriteInto(context, responseContext, "UTF-8", f).writeInto(buffer);
    }

}
