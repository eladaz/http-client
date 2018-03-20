package http.client;

import java.util.Properties;

import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;


/**
 * HTTP client factory.
 * 
 * @author elad.avraham@hp.com
 * 
 */
public class HttpClientFactory {

    public static HttpClient create(Properties httpConnectionParameters) {
        DecompressingHttpClient httpClient = new DecompressingHttpClient(new SystemDefaultHttpClient());
        configureHttpClient(httpClient);
        return create(new HttpClientRequestInvoker(httpClient), httpConnectionParameters);

    }

    private static void configureHttpClient(DecompressingHttpClient httpClient) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static HttpClient create(RequestInvoker invoker, Properties httpConnectionParameters) {
		return new HttpClientImpl(invoker , httpConnectionParameters);
	}

    public static HttpClient create(RequestInvoker invoker) {
        return new HttpClientImpl(invoker);
    }
}
