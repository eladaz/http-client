package http.client;

import java.util.Map;

/**
 *
 * Lightweight HTTP client that provides support to common HTTP methods e.g get,
 * post, put, and delete. In addition the client is responsible for authentication and session management.
 *
 * @author elad.avarahm@hp.com
 *
 */
public interface HttpClient {

    /**
     *
     * Sends a GET request to the specified URI.
     *
     * @param uri
     *            The resource relative URI.
     * @return The request result.
     *
     */
    public Response doGet(String uri);

    /**
     *
     * Sends a GET request to the specified URI.
     *
     * @param uri
     *            The resource relative URI.
     * @param headers
     *            The headers to use in the request.
     * @return The request result.
     *
     */
    public Response doGet(String uri, Map<String, String> headers);

    /**
     * Sends a PUT request to the specified URI using
     * {@link javax.ws.rs.core.MediaType#APPLICATION_JSON} as
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to put.
     * @return The request result.
     *
     */
    public Response doPut(String uri, String payload);

    /**
     * Sends a PUT request to the specified URI. In case the
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header is not specified
     * adding{@link javax.ws.rs.core.MediaType#APPLICATION_JSON} as default
     * content type.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to put.
     * @param headers
     *            The headers to use in the request.
     * @return The request result.
     *
     */
    public Response doPut(String uri, Map<String, String> headers,
                          String payload);

    /**
     * Sends a POST request to the specified URI using
     * {@link javax.ws.rs.core.MediaType#APPLICATION_JSON} as
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to post as string.
     * @return The request result.
     *
     */
    public Response doPost(String uri, String payload);

    /**
     * Sends a POST request to the specified URI. In case the
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header is not specified
     * adding{@link javax.ws.rs.core.MediaType#APPLICATION_JSON} as default
     * content type.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to put.
     * @param headers
     *            The headers to use in the request.
     * @return The request result.
     *
     */
    public Response doPost(String uri, Map<String, String> headers,
                           String payload);

    /**
     * Sends a POST request to the specified URI using
     * {@link javax.ws.rs.core.MediaType#APPLICATION_OCTET_STREAM} as
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to post as byte array.
     * @return The request result.
     *
     */
    public Response doPost(String uri, byte[] payload);

    /**
     * Sends a POST request to the specified URI. In case the
     * {@link javax.ws.rs.core.HttpHeaders#CONTENT_TYPE} header is not specified
     * adding{@link javax.ws.rs.core.MediaType#APPLICATION_OCTET_STREAM} as default content type.
     *
     * @param uri
     *            The resource relative URI.
     * @param payload
     *            The payload to post as byte array.
     * @param headers
     *            The headers to use in the request.
     * @return The request result.
     *
     */
    public Response doPost(String uri,  Map<String, String> headers, byte[] payload);

    /**
     *
     * Sends a Delete request to the specified URI.
     *
     * @param uri
     *            The resource relative URI.
     * @return The request result.
     *
     */
    public Response doDelete(String uri);

}
