package http.client;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * HTTP Client implementation based on the contract of {@link HttpClient}.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public class HttpClientRequestInvoker implements RequestInvoker {
	private final HttpClient httpClient;
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientRequestInvoker.class);

	public HttpClientRequestInvoker(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public Response doGet(URI url, Map<String, String> headers)
			throws HttpClientException {
		HttpGet request = new HttpGet(url);
		HttpResponse httpResponse = doHttp(url, request, headers);
		return new HttpClientResponse(httpResponse, url);
	}

	@Override
	public Response doPut(URI url, String payload,
			Map<String, String> headers) {
		HttpPut request = new HttpPut(url);
		request.setEntity(new StringEntity(payload,	ContentType.APPLICATION_JSON));
		HttpResponse httpResponse = doHttp(url, request, headers);
		return new HttpClientResponse(httpResponse, url);
	}

	@Override
	public Response doDelete(URI url, Map<String, String> headers) {
		HttpDelete request = new HttpDelete(url);
		HttpResponse httpResponse = doHttp(url, request, headers);
		return new HttpClientResponse(httpResponse, url);
	}

	@Override
	public Response doPost(URI url, String payload,	Map<String, String> headers) {
		return postEntity(url, new StringEntity(payload, ContentType.APPLICATION_JSON), headers);
	}

	@Override
	public Response doPost(URI url, byte[] payload,
			Map<String, String> headers) {

		return postEntity(url, new ByteArrayEntity(payload,	ContentType.APPLICATION_OCTET_STREAM), headers);
	}

	private Response postEntity(URI url, HttpEntity entity,
			Map<String, String> headers) {
		HttpPost request = new HttpPost(url);
		request.setEntity(entity);
		HttpResponse httpResponse = doHttp(url, request, headers);
		return new HttpClientResponse(httpResponse, url);
	}

	private HttpResponse doHttp(URI url, HttpUriRequest request, Map<String, String> headers) throws HttpClientException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Executing request to: " + url + ", method : "	+ request.getMethod(), ", headers: " + headers);
		}
		
		for (Entry<String, String> header : headers.entrySet()) {
			request.addHeader(new BasicHeader(header.getKey(), header.getValue()));
		}

		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(request);
		} catch (IOException e) {
            HttpClientUtils.closeQuietly(httpResponse);
            String message = "Failed to execute request to: "+ url;
            LOGGER.error(message, e);
			throw new HttpClientException(e, message, url.toString());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Request completed successfully. Status: " + httpResponse.getStatusLine());
		}
		
		return httpResponse;
	}
}
