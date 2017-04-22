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
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Deletes the bucket specified. Only buckets that contain no version of any files can be deleted.</p>
 * 
 * This is the interaction class for the <strong>b2_delete_bucket</strong> api 
 * calls, this was generated from the backblaze api documentation - which can 
 * be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_delete_bucket.html">http://www.backblaze.com/b2/docs/b2_delete_bucket.html</a>
 * 
 * @author synapticloop
 */
public class B2DeleteBucketRequest extends BaseB2Request {
	private static final String B2_DELETE_BUCKET = BASE_API_VERSION + "b2_delete_bucket";

	/**
	 * Instantiate a new delete bucket request
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId The id of the bucket to delete
	 */
	public B2DeleteBucketRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_DELETE_BUCKET);

		this.addProperty(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
	}

	/**
	 * return the deleted bucket response
	 * 
	 * @return The deleted bucket response
	 * 
	 * @throws B2ApiException if there was an error with the call, or if you are
	 *     trying to delete a bucket which is not empty
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2BucketResponse getResponse() throws B2ApiException, IOException {
		return new B2BucketResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
