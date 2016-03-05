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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2Exception;

public class B2BucketResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DownloadFileResponse.class);

	private final String bucketId;
	private final String accountId;
	private final String bucketName;
	private final String bucketType;

	/**
	 * Instantiate an bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2Exception if there was an error parsing the response
	 */
	public B2BucketResponse(String json) throws B2Exception {
		super(json);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketName = response.optString(B2ResponseProperties.KEY_BUCKET_NAME, null);
		this.bucketType = response.optString(B2ResponseProperties.KEY_BUCKET_TYPE, null);

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_BUCKET_ID);
			response.remove(B2ResponseProperties.KEY_ACCOUNT_ID);
			response.remove(B2ResponseProperties.KEY_BUCKET_NAME);
			response.remove(B2ResponseProperties.KEY_BUCKET_TYPE);

			warnOnMissedKeys(LOGGER, response);
		}
	}

	/**
	 * Instantiate an bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param JSONObject The pre-parsed jsonObject
	 * 
	 * @throws B2Exception if there was an error parsing the response
	 */
	public B2BucketResponse(final JSONObject response) throws B2Exception {
		super(response);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketName = response.optString(B2ResponseProperties.KEY_BUCKET_NAME, null);
		this.bucketType = response.optString(B2ResponseProperties.KEY_BUCKET_TYPE, null);
	}

	/**
	 * Get the bucket id
	 * 
	 * @return the id of the bucket
	 */
	public String getBucketId() { return this.bucketId; }

	/**
	 * Get the account id
	 * 
	 * @return the id of the account
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Get the name of the bucket
	 * 
	 * @return the name of the bucket
	 */
	public String getBucketName() { return this.bucketName; }

	/**
	 * Get the type of the bucket, on of allPrivate or allPublic
	 * 
	 * @return The bucket type
	 */
	public BucketType getBucketType() { return BucketType.valueOf(this.bucketType); }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2BucketResponse{");
		sb.append("bucketId='").append(bucketId).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append(", bucketName='").append(bucketName).append('\'');
		sb.append(", bucketType='").append(bucketType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
