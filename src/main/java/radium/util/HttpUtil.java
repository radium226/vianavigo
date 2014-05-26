package radium.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.DefaultRoutePlanner;

public class HttpUtil {

	public static CloseableHttpClient newClient() {
		CloseableHttpClient client = newClientBuilder().build();
		return client; 
	}
	
	public static HttpClientBuilder newClientBuilder() {
		HttpClientBuilder builder = HttpClientBuilder.create();
		return builder;
	}
	
	protected static HttpClientBuilder withProxy(HttpClientBuilder builder, HttpHost proxyHost, Credentials proxyCredentials) {
		builder
			.setRoutePlanner(newRoutePlannerForProxy(proxyHost))
			.setDefaultCredentialsProvider(newCredentialsProviderForProxy(proxyHost, proxyCredentials));
		return builder;
	}
	
	protected static DefaultRoutePlanner newRoutePlannerForProxy(HttpHost proxyHost) {
		DefaultProxyRoutePlanner proxyRoutePlanner = new DefaultProxyRoutePlanner(proxyHost);
		return proxyRoutePlanner;
	}
	
	protected static CredentialsProvider newCredentialsProviderForProxy(HttpHost proxyHost, Credentials proxyCredentials) {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(proxyHost), proxyCredentials);
		return credentialsProvider;
	}
	
	public static CloseableHttpClient newClientWithProxy(String proxyHostName, int proxyPort, String proxyUser, String proxyPassword) {
		return newClientWithProxy(new HttpHost(proxyHostName, proxyPort), new UsernamePasswordCredentials(proxyUser, proxyPassword));
	}
	
	public static CloseableHttpClient newClientWithProxy(HttpHost host, Credentials credentials) {
		return withProxy(newClientBuilder(), host, credentials).build();
	}
	
}
