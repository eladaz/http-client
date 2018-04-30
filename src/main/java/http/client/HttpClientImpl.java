package http.client;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.APPLICATION_OCTET_STREAM;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.protocol.HTTP;


/**
 * 
 * Implementation of {@link HttpClient}.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public class HttpClientImpl implements HttpClient {

	private final String baseURL;
	private final RequestInvoker requestInvoker;
	private static final String MOCK_BASE_URL = "http://localhost:8080/platform-webapp/rest/mockTenant/opb/remoting/";
	
	public HttpClientImpl(RequestInvoker httpClientRequestInvoker) {
		this.baseURL = System.getProperty("base.url", System.getProperty("base.url", MOCK_BASE_URL));
		this.requestInvoker= httpClientRequestInvoker;
	}

	public HttpClientImpl(RequestInvoker httpClientRequestInvoker, Properties connectionParameters) {
		this.baseURL = connectionParameters.getProperty("base.url", System.getProperty("base.url", MOCK_BASE_URL));
		this.requestInvoker= httpClientRequestInvoker;
	}


	@Override
	public Response doGet(String uri) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doGet(url, Collections.<String, String>emptyMap());
		return handleResponse(uri, response);
	}

	@Override
	public Response doGet(String uri, Map<String, String> headers) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doGet(url, headers);
		return handleResponse(uri, response);
	}

	@Override
	public Response doPut(String uri, String payload) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doPut(url, payload,	getDefaultHeaders());
		return handleResponse(uri, response);
	}


	@Override
	public Response doPut(String uri, Map<String, String> headers, String payload) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doPut(url, payload,	addDefaultHeaders(headers));
		return handleResponse(uri, response);
	}

	@Override
	public Response doPost(String uri, String payload) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doPost(url, payload, getDefaultHeaders());
		return handleResponse(uri, response);
	}

	@Override
	public Response doPost(String uri, Map<String, String> headers,	String payload) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doPost(url, payload, addDefaultHeaders(headers));
		return handleResponse(uri, response);
	}
	
	@Override
	public Response doPost(String uri, byte[] payload) {
		URI url = buildURL(uri);
		Map<String, String> headers = getDefaultHeaders();
		headers.put(HTTP.CONTENT_TYPE, APPLICATION_OCTET_STREAM.getMimeType());
		Response response = requestInvoker.doPost(url, payload, headers );
		return handleResponse(uri, response);
	}

	@Override
	public Response doPost(String uri, Map<String, String> headers, byte[] payload) {
		URI url = buildURL(uri);
		Response response = requestInvoker.doPost(url, payload, addDefaultHeaders(headers, true));
		return handleResponse(uri, response);
	}

	@Override
	public Response doDelete(String uri) throws HttpClientException {
		URI url = buildURL(uri);
		Response response = requestInvoker.doDelete(url, getDefaultHeaders());
		return handleResponse(uri, response);
	}

	private URI buildURL(String uri) {
		return URI.create(baseURL + uri);
	}
	
	private Response handleResponse(String uri, Response response) throws HttpClientException {
		int statusCode = response.getStatusCode();
		if (statusCode >= 300) {
			// TODO: Provide better exception handling based on MaaS exception format and exception types.
			String message = MessageFormat.format("Failed to execute request to {0}. Message: {1}. Status code: {2}",
							uri, response.getStatusText(), statusCode);
			throw new HttpClientException(statusCode, response.getStatusText(),	message, uri);
		}
		return response;
	}
	
	private Map<String, String> getDefaultHeaders() {
		return addDefaultHeaders(Collections.<String, String>emptyMap());
	}

	private Map<String, String> addDefaultHeaders(Map<String,String> headers) {
		return addDefaultHeaders(headers, false);
	}
	
	private Map<String, String> addDefaultHeaders(Map<String,String> headers, boolean octet) {
		HashMap<String, String> requestHeaders = new HashMap<>(headers);
		
		if (!headers.containsKey(CONTENT_TYPE)) {
			requestHeaders.put(CONTENT_TYPE, octet? APPLICATION_OCTET_STREAM.getMimeType(): APPLICATION_JSON.getMimeType());
		}
		return requestHeaders;
	}
}
