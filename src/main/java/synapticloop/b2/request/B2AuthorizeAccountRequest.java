package synapticloop.b2.request;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
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

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;

/**
 * <p>Used to log in to the B2 API. Returns an authorization token that can be 
 * used for account-level operations, and a URL that should be used as the base 
 * URL for subsequent API calls.</p>
 * 
 * An HTTP basic auth value constructed as follows:
 * <ul>
 *   <li>The B2 account id and B2 application key for the account are combined 
 *   into a string in the format "accountId:applicationKey".</li>
 *   <li>The combined string is Base64 encoded.</li>
 *   <li>"Basic " is put before the encoded string.</li>
 * </ul>
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
	 * @param client Shared HTTP client instance
	 * @param accountId the account id
	 * @param applicationKey the application key
	 */
	public B2AuthorizeAccountRequest(CloseableHttpClient client, String accountId,  String applicationKey) {

		super(client, B2_AUTHORIZE_ACCOUNT);

		this.addHeader(HttpHeaders.AUTHORIZATION,  String.format("Basic %s", Base64.encodeBase64String((String.format("%s:%s", accountId, applicationKey)).getBytes())));
	}

	/**
	 * Execute the call and return the authorize response
	 * 
	 * @return the authorize response
	 * 
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2AuthorizeAccountResponse getResponse() throws B2ApiException, IOException {
		final CloseableHttpResponse httpResponse = executeGet();
		return new B2AuthorizeAccountResponse(EntityUtils.toString(httpResponse.getEntity()));
	}
}
