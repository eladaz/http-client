package http.client;

import static org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP client response of {@link HttpClient}.
 * 
 * @author elad.avraham@hp.com
 * 
 */
class HttpClientResponse implements Response {

	private final HttpResponse httpResponse;
	private final URI url;
	private int statusCode;
	private String statusText;
	private long contentLength;
	private String contentType;
	private String responseString;
	private byte[] responseBytes;
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientResponse.class);

	public HttpClientResponse(HttpResponse httpResponse, URI url) throws HttpClientException {
		this.httpResponse = httpResponse;
		this.url = url;
		setStatusCode(httpResponse);
		setStatusText(httpResponse);
		setContentLength(httpResponse);
		setContentType(httpResponse);
		readResponse();
	}

	private void readResponse() {
		try {
			readResponseImpl();
		} catch (IOException e) {
			String message = "Failed to read response for: " + url;
			LOGGER.error(message);
			throw new HttpClientException(e, message, url.toString());
		} finally {
			HttpClientUtils.closeQuietly(httpResponse);
		}
	}

	private void readResponseImpl() throws IOException {
		HttpEntity entity = httpResponse.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		// TODO: If isStreaming() returns true do not read the entire content
		if (APPLICATION_OCTET_STREAM.getMimeType().equals(contentType.getMimeType())) {
			responseBytes = EntityUtils.toByteArray(entity);
		} else {
			Charset charset = contentType.getCharset() != null ? contentType.getCharset() : Consts.UTF_8;
			responseString = EntityUtils.toString(entity, charset);
            contentLength = responseString != null ? responseString.length() : -1;
		}
	}

	private void setStatusText(HttpResponse httpResponse) {
		if (httpResponse.getStatusLine() != null) {
			statusText = httpResponse.getStatusLine().getReasonPhrase();
		}
	}

	private void setStatusCode(HttpResponse httpResponse) {

		if (httpResponse.getStatusLine() != null) {
			statusCode = httpResponse.getStatusLine().getStatusCode();
		}
	}

	private void setContentLength(HttpResponse httpResponse) {
		contentLength = httpResponse.getEntity() != null ? httpResponse.getEntity().getContentLength() : -1;
	}

	private void setContentType(HttpResponse httpResponse) {
		if (httpResponse.getEntity() != null
				&& httpResponse.getEntity().getContentType() != null) {
			contentType = httpResponse.getEntity().getContentType().getValue();
		}
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getStatusText() {
		return statusText;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public long getContentLength() {
		return contentLength;
	}

	@Override
	public String getResponseString() {
		return responseString;
	}

	@Override
	public byte[] getResponseBytes() {
		return responseBytes;
	}
	
	@Override
	public boolean isStreaming() {
		HttpEntity entity = httpResponse.getEntity();
		return entity != null && entity.isStreaming();
	}

	@Override
	public InputStream getResponseStream() throws IllegalStateException, IOException {
		return httpResponse.getEntity().getContent();
	}

	@Override
	public String getHeader(String name) {
		Header[] values = httpResponse.getHeaders(name);
		if (values == null || values.length == 0) {
			return null;
		} else {
			return values[0].getValue();
		}
	}

	@Override
	public List<String> getHeaders(String name) {
		Header[] values = httpResponse.getHeaders(name);
		if (values == null) {
			return Collections.emptyList();
		} else {
			List<String> list = new ArrayList<>();
			for (Header value : values) {
				list.add(value.getValue());
			}
			return list;
		}
	}

	@Override
	public Map<String, List<String>> listHeaders() {
		Header[] headers = httpResponse.getAllHeaders();
		if (headers == null) {
			return Collections.emptyMap();
		} else {
			Map<String, List<String>> map = new HashMap<>();
			for (Header header : headers) {
				String name = header.getName();
				List<String> values = map.get(name);
				if (values == null) {
					values = new LinkedList<>();
					map.put(name, values);
				}
				values.add(header.getValue());
			}
			return map;
		}
	}

	@Override
	public String toString() {
		return httpResponse.toString();
	}
}