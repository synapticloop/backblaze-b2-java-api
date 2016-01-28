package synapticloop.b2.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2AuthorizeAccountResponse.class);

	private String accountId = null;
	private String apiUrl = null;
	private String authorizationToken = null;
	private String downloadUrl = null;

	/**
	 * Instantiate an authorize account response with the JSON response as a 
	 * string from the API call.  This response is then parsed into the 
	 * relevant fields.
	 * 
	 * @param response the response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2AuthorizeAccountResponse(String response) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(response);

		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		jsonObject.remove(KEY_ACCOUNT_ID);

		this.apiUrl = jsonObject.optString(KEY_API_URL);
		jsonObject.remove(KEY_API_URL);

		this.authorizationToken = jsonObject.optString(KEY_AUTHORIZATION_TOKEN);
		jsonObject.remove(KEY_AUTHORIZATION_TOKEN);

		this.downloadUrl = jsonObject.optString(KEY_DOWNLOAD_URL);
		jsonObject.remove(KEY_DOWNLOAD_URL);

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	/**
	 * Return the account ID used to authorize this account
	 * 
	 * @return the account ID
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * The API URL to be used for all subsequent calls to the API
	 * 
	 * @return the api url to use for all subsequent calls
	 */
	public String getApiUrl() { return this.apiUrl; }

	/**
	 * Return the authorization token to be used for all subsequent calls
	 * 
	 * @return the authorization token to be used for all subsequent calls
	 */
	public String getAuthorizationToken() { return this.authorizationToken; }

	/**
	 * Return the url to be used for downloading files
	 * 
	 * @return the url to be used for downloading files
	 */
	public String getDownloadUrl() { return this.downloadUrl; }
}
