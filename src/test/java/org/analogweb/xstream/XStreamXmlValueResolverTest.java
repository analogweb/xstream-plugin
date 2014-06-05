package org.analogweb.xstream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.analogweb.Headers;
import org.analogweb.RequestContext;
import org.analogweb.core.MediaTypes;
import org.analogweb.xstream.model.Foo;
import org.junit.Before;
import org.junit.Test;

/**
 * @author snowgoose
 */
public class XStreamXmlValueResolverTest {

    private XStreamXmlValueResolver mapper;
    private RequestContext requestContext;
    private Headers headers;

    @Before
    public void setUp() throws Exception {
        mapper = new XStreamXmlValueResolver();
        requestContext = mock(RequestContext.class);
        headers = mock(Headers.class);
    }

    @Test
    public void testMapToType() throws Exception {
        when(requestContext.getRequestHeaders()).thenReturn(headers);
        when(headers.getValues("Content-Type")).thenReturn(Arrays.asList("application/xml"));
        InputStream from = new ByteArrayInputStream(
                "<?xml version=\"1.0\" ?><org.analogweb.xstream.model.Foo><name>foo</name><age>34</age></org.analogweb.xstream.model.Foo>"
                        .getBytes());
        when(requestContext.getRequestBody()).thenReturn(from);
        Foo actual = (Foo) mapper.resolveValue(requestContext, null, null, Foo.class, null);
        assertThat(actual.getName(), is("foo"));
        assertThat(actual.getAge(), is(34));
        // TODO
        //        assertThat(actual.getBirthDay(), is(new SimpleDateFormat("yyyyMMdd").parse("19780420")));
    }

    @Test
    public void testMapToTypeWithoutXmlStream() throws Exception {
        when(requestContext.getRequestHeaders()).thenReturn(headers);
        when(headers.getValues("Content-Type")).thenReturn(Arrays.asList("application/xml"));
        InputStream from = new ByteArrayInputStream("XXX".getBytes());
        when(requestContext.getRequestBody()).thenReturn(from);
        Foo actual = (Foo) mapper.resolveValue(requestContext, null, null, Foo.class, null);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testMapToTypeWithIOError() throws Exception {
        when(requestContext.getRequestHeaders()).thenReturn(headers);
        when(headers.getValues("Content-Type")).thenReturn(Arrays.asList("application/x-d"));
        InputStream from = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
        when(requestContext.getRequestBody()).thenReturn(from);
        Foo actual = (Foo) mapper.resolveValue(requestContext, null, null, Foo.class, null);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testMapToTypeAnotherContentType() throws Exception {
        when(requestContext.getRequestHeaders()).thenReturn(headers);
        when(headers.getValues("Content-Type")).thenReturn(Arrays.asList("application/x-d"));
        InputStream from = new ByteArrayInputStream("???".getBytes());
        when(requestContext.getRequestBody()).thenReturn(from);
        Foo actual = (Foo) mapper.resolveValue(requestContext, null, null, Foo.class, null);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testSupports() {
        assertThat(mapper.supports(MediaTypes.TEXT_XML_TYPE), is(true));
        assertThat(mapper.supports(MediaTypes.APPLICATION_XML_TYPE), is(true));
        assertThat(mapper.supports(MediaTypes.APPLICATION_SVG_XML_TYPE), is(true));
        assertThat(mapper.supports(MediaTypes.APPLICATION_ATOM_XML_TYPE), is(true));

        assertThat(mapper.supports(MediaTypes.APPLICATION_JSON_TYPE), is(false));
        assertThat(mapper.supports(MediaTypes.TEXT_PLAIN_TYPE), is(false));
    }
}
