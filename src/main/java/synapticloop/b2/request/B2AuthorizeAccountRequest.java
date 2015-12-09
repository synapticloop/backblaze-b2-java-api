package synapticloop.b2.request;

import java.util.Base64;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;

/**
 * <p>Used to log in to the B2 API. Returns an authorization token that can be 
 * used for account-level operations, and a URL that should be used as the base 
 * URL for subsequent API calls.</p>
 * 
 * This is the interaction class for the <strong>b2_authorize_account</strong> api 
 * calls, this was generated from the backblaze api documentation - which can be 
 * found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_authorize_account.html">http://www.backblaze.com/b2/docs/b2_authorize_account.html</a>
 * 
 * @author synapticloop
 */

public class B2AuthorizeAccountRequest extends BaseB2Request {
	private static final String B2_AUTHORIZE_ACCOUNT = BASE_API + "b2_authorize_account";

	/**
	 * Instantiate a new authorize account request
	 * 
	 * @param accountId the account id
	 * @param applicationKey the application key
	 */
	public B2AuthorizeAccountRequest(String accountId, String applicationKey) {
		super(null);
		url = B2_AUTHORIZE_ACCOUNT;
		headers.put(REQUEST_PROPERTY_AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((accountId + ":" + applicationKey).getBytes()));
	}

	/**
	 * Execute the call and return the authorize response
	 * 
	 * @return the authorize response
	 * 
	 * @throws B2ApiException if there was an error with the call
	 */
	public B2AuthorizeAccountResponse getResponse() throws B2ApiException {
		return(new B2AuthorizeAccountResponse(executeGet()));
	}
}
