package synapticloop.b2.response;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import synapticloop.b2.exception.B2ApiException;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2AuthorizeAccountResponse.class);

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
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2AuthorizeAccountResponse(String json) throws B2ApiException {
		super(json);

		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.apiUrl = response.optString(B2ResponseProperties.KEY_API_URL, null);
		this.authorizationToken = response.optString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN, null);
		this.downloadUrl = response.optString(B2ResponseProperties.KEY_DOWNLOAD_URL, null);

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_ACCOUNT_ID);
			response.remove(B2ResponseProperties.KEY_API_URL);
			response.remove(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);
			response.remove(B2ResponseProperties.KEY_DOWNLOAD_URL);

			warnOnMissedKeys(LOGGER, response);
		}
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
	 * Get authorization token to use with all calls, other than b2_authorize_account, 
	 * that need an Authorization header. This authorization token is valid for at 
	 * most 24 hours.
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
