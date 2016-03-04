package synapticloop.b2.response;

import synapticloop.b2.exception.B2Exception;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private final String accountId;
	private final String apiUrl;
	private final String authorizationToken;
	private final String downloadUrl;

	/**
	 * Instantiate an authorize account response with the JSON response as a 
	 * string from the API call.  This response is then parsed into the 
	 * relevant fields.
	 * 
	 * @param json the response (in JSON format)
	 * 
	 * @throws B2Exception if there was an error parsing the response
	 */
	public B2AuthorizeAccountResponse(String json) throws B2Exception {
		super(json);

		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.apiUrl = response.optString(B2ResponseProperties.KEY_API_URL);
		this.authorizationToken = response.optString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);
		this.downloadUrl = response.optString(B2ResponseProperties.KEY_DOWNLOAD_URL);
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2AuthorizeAccountResponse{");
		sb.append("accountId='").append(accountId).append('\'');
		sb.append(", apiUrl='").append(apiUrl).append('\'');
		sb.append(", downloadUrl='").append(downloadUrl).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
