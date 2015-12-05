package synapticloop.b2.request;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;

/**
 * <p>Used to log in to the B2 API. Returns an authorization token that can be used for account-level operations, and a URL that should be used as the base URL for subsequent API calls.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_authorize_account</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_authorize_account.html">http://www.backblaze.com/b2/docs/b2_authorize_account.html</a>
 * 
 * @author synapticloop
 */

public class B2AuthorizeAccountRequest extends BaseB2Request {
	private static final String API_URL_BASE = "https://api.backblaze.com/b2api/v1/";

	private String accountId = null;
	private String applicationKey = null;

	public B2AuthorizeAccountRequest(String accountId, String applicationKey) {
		this.accountId = accountId;
		this.applicationKey = applicationKey;
	}

	public B2AuthorizeAccountResponse getResponse() throws B2ApiException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(API_URL_BASE + "b2_authorize_account");
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((accountId + ":" + applicationKey).getBytes()));
			inputStream = new BufferedInputStream(connection.getInputStream());
			return(new B2AuthorizeAccountResponse(inputStream));
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			if(null != inputStream) { try { inputStream.close(); } catch (IOException ex) {} }
			if(null != connection) { connection.disconnect(); }
		}
	}
}
