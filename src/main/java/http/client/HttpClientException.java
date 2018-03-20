package http.client;


/**
 * 
 * Generic HTTP Client exception. TODO: Provide better exception handling based
 * on status code, MaaS exception format and exception types.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public class HttpClientException extends RuntimeException {
	private static final long serialVersionUID = 7961374319117992042L;
	private int statusCode;
	private String uri;
	private  String statusText;

	public HttpClientException(int httpStatus, String statusText, String message, String uri) {
		super(message);
		this.statusCode = httpStatus;
		this.statusText = statusText;
		this.uri = uri;
	}
	

	public HttpClientException(Throwable t, String message, String uri) {
		super(message,t);
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public String getStatusText() {
		return statusText;
	}
}
