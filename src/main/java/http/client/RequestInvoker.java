package http.client;

import java.net.URI;
import java.util.Map;


/**
 * 
 * 
 * Represents request invoker for {@link HttpClientImpl}.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public interface RequestInvoker {
	/**
	 * 
	 * Sends a Get request to the specified URI using the given headers.
	 * 
	 * @param url
	 *            The resource relative URI.
	 * @param headers
	 *            The headers to use in the request.
	 * @return The request result.
	 */
	Response doGet(URI url, Map<String, String> headers);

	/**
	 * Sends a Put request to the specified URI using the given headers.
	 * 
	 * @param url
	 *            The resource relative URI.
	 * @param payload
	 *            The payload to put.
	 * @param headers
	 *            The headers to use in the request.
	 * @return The request result.
	 */
	Response doPut(URI url, String payload, Map<String, String> headers);

	/**
	 * 
	 * Sends a Delete request to the specified URI using the given headers.
	 * 
	 * @param url
	 *            The resource relative URI.
	 * @param headers
	 *            The headers to use in the request.
	 * @return The request result.
	 */
	Response doDelete(URI url, Map<String, String> headers);

	/**
	 * 
	 * Sends a Post request to the specified URI using the given headers.
	 * 
	 * @param url
	 *            The resource relative URI.
	 * @param payload
	 *            The payload to put as string.
	 * @param headers
	 *            The headers to use in the request.
	 * @return The request result.
	 */
	Response doPost(URI url, String payload, Map<String, String> headers);

	/**
	 * 
	 * Sends a Post request to the specified URI using the given headers.
	 * 
	 * @param url
	 *            The resource relative URI.
	 * @param payload
	 *            The payload to put as byte array.
	 * @param headers
	 *            The headers to use in the request.
	 * @return The request result.
	 */
	Response doPost(URI url, byte[] payload, Map<String, String> headers);
}
