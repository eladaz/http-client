package http.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Represents {@link HttpClient} response.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public interface Response {

	/**
	 * Obtains the status code of this response.
	 * 
	 * 
	 * @return the status code, or <code>0</code> if the status is not set.
	 */
	int getStatusCode();

	/**
	 * Obtains the status text of this response.
	 * 
	 * 
	 * @return the status line, or <code>null</code> if the status is not set.
	 */
	String getStatusText();

	/**
	 * Returns the length of the content, if known.
	 * 
	 * @return the number of bytes of the content, or a negative number if
	 *         unknown. If the content length is known but exceeds
	 *         {@link java.lang.Long#MAX_VALUE Long.MAX_VALUE}, a negative
	 *         number is returned.
	 */

	String getContentType();

	long getContentLength();

	/**
	 * @return
	 */
	String getResponseString();

	/**
	 * @return
	 */
	byte[] getResponseBytes();

	/**
	 * Tells whether this response depends on an underlying stream. Streamed
	 * response that read data directly from the socket return <code>true</code>. Self-contained response return <code>false</code>.
	 * 
	 * @return <code>true</code> if the response content is streamed,
	 *         <code>false</code> otherwise
	 */
	boolean isStreaming();

	/**
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	InputStream getResponseStream() throws IllegalStateException, IOException;

	/**
	 * Returns the first value of the specified header.
     * @param header
	 * @return
	 */
	String getHeader(String header);

	/**
     * Returns the values list of the specified header.
	 * @param header
	 * @return
	 */
	List<String> getHeaders(String header);

	/**
	 * @return
	 */
	Map<String, List<String>> listHeaders();

}
