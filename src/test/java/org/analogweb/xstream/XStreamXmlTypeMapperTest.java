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
import org.analogweb.xstream.model.Foo;
import org.junit.Before;
import org.junit.Test;

/**
 * @author snowgoose
 */
public class XStreamXmlTypeMapperTest {

    private XStreamXmlTypeMapper mapper;
    private RequestContext requestContext;
    private Headers headers;

    @Before
    public void setUp() throws Exception {
        mapper = new XStreamXmlTypeMapper();
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
        Foo actual = (Foo) mapper.resolveAttributeValue(requestContext, null, null, Foo.class);
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
        Foo actual = (Foo) mapper.resolveAttributeValue(requestContext, null, null, Foo.class);
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
        Foo actual = (Foo) mapper.resolveAttributeValue(requestContext, null, null, Foo.class);
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void testMapToTypeAnotherContentType() throws Exception {
        when(requestContext.getRequestHeaders()).thenReturn(headers);
        when(headers.getValues("Content-Type")).thenReturn(Arrays.asList("application/x-d"));
        InputStream from = new ByteArrayInputStream("???".getBytes());
        when(requestContext.getRequestBody()).thenReturn(from);
        Foo actual = (Foo) mapper.resolveAttributeValue(requestContext, null, null, Foo.class);
        assertThat(actual, is(nullValue()));
    }

}
