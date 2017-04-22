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

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListBucketsResponse;

/**
 * <p>Lists buckets associated with an account, in alphabetical order by bucket ID.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_list_buckets</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_list_buckets.html">http://www.backblaze.com/b2/docs/b2_list_buckets.html</a>
 * 
 * @author synapticloop
 */
public class B2ListBucketsRequest extends BaseB2Request {
	private static final String B2_LIST_BUCKETS = BASE_API_VERSION + "b2_list_buckets";

	/**
	 * Create a List buckets request
	 * 
	 * @param client the HTTP client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 */
	public B2ListBucketsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_BUCKETS);

		this.addProperty(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
	}

	/**
	 * Return the list buckets response 
	 * 
	 * @return the list buckets response
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListBucketsResponse getResponse() throws B2ApiException, IOException {
		return new B2ListBucketsResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
