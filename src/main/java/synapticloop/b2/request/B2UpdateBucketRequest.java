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
import org.json.JSONArray;

import synapticloop.b2.BucketType;
import synapticloop.b2.LifecycleRule;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Update an existing bucket.</p>
 * 
 * <p>Modifies the bucketType of an existing bucket. Can be used to allow everyone to download the contents of the bucket without providing any authorization, or to prevent anyone from downloading the contents of the bucket without providing a bucket auth token.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_update_bucket</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_update_bucket.html">http://www.backblaze.com/b2/docs/b2_update_bucket.html</a>
 * 
 * @author synapticloop
 */
public class B2UpdateBucketRequest extends BaseB2Request {

	private static final String B2_UPDATE_BUCKET = BASE_API_VERSION + "b2_update_bucket";

	/**
	 * Create an update bucket request
	 * 
	 * @param client The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId the id of the bucket to change
	 * @param bucketType the type of bucket to change to
	 */
	public B2UpdateBucketRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, BucketType bucketType, LifecycleRule... lifecycleRules) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_UPDATE_BUCKET);

		this.addProperty(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		this.addProperty(B2RequestProperties.KEY_BUCKET_TYPE, bucketType.toString());
		this.addProperty(B2RequestProperties.KEY_LIFECYCLE_RULES, new JSONArray(lifecycleRules));
	}

	/**
	 * Return the bucket response 
	 * 
	 * @return the bucket response
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2BucketResponse getResponse() throws B2ApiException, IOException {
		return new B2BucketResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
