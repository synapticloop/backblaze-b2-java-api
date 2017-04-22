package synapticloop.b2.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.BucketType;
import synapticloop.b2.LifecycleRule;
import synapticloop.b2.exception.B2ApiException;

public class B2BucketResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2BucketResponse.class);

	private final String bucketId;
	private final String accountId;
	private final String bucketName;
	private final String bucketType;
	private final Long revision;
	private final Map<String, String> bucketInfo;
	private final List<LifecycleRule> lifecycleRules = new ArrayList<LifecycleRule>();

	/**
	 * Instantiate a bucket response with the JSON response as a string from
	 * the API call.  This response is then parsed into the relevant fields.
	 *
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2BucketResponse(String json) throws B2ApiException {
		super(json);

		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketName = this.readString(B2ResponseProperties.KEY_BUCKET_NAME);
		this.bucketType = this.readString(B2ResponseProperties.KEY_BUCKET_TYPE);
		this.revision = this.readLong(B2ResponseProperties.KEY_REVISION);
		this.bucketInfo = this.readMap(B2ResponseProperties.KEY_BUCKET_INFO);

		JSONArray lifecycleObjects = this.readObjects(B2ResponseProperties.KEY_LIFECYCLE_RULES);
		for (Object object : lifecycleObjects) {
			lifecycleRules.add(new LifecycleRule((JSONObject)object));
		}

		this.warnOnMissedKeys();
	}

	/**
	 * Instantiate a bucket response with the JSON response as a string from
	 * the API call.  This response is then parsed into the relevant fields.
	 *
	 * @param response The pre-parsed jsonObject
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2BucketResponse(final JSONObject response) throws B2ApiException {
		super(response);

		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketName = this.readString(B2ResponseProperties.KEY_BUCKET_NAME);
		this.bucketType = this.readString(B2ResponseProperties.KEY_BUCKET_TYPE);
		this.revision = this.readLong(B2ResponseProperties.KEY_REVISION);
		this.bucketInfo = this.readMap(B2ResponseProperties.KEY_BUCKET_INFO);

		JSONArray lifecycleObjects = this.readObjects(B2ResponseProperties.KEY_LIFECYCLE_RULES);
		for (Object object : lifecycleObjects) {
			lifecycleRules.add(new LifecycleRule((JSONObject)object));
		}

		this.warnOnMissedKeys();
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
	 * Get the type of the bucket, one of 'allPrivate' or 'allPublic'
	 *
	 * @return The bucket type
	 */
	public BucketType getBucketType() {
		try {
			return BucketType.valueOf(this.bucketType);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Get the map of the bucket info for the bucket that was operated on, or an 
	 * empty map if not set.
	 * 
	 * @return the map of the file info for the file that was operated on
	 */
	public Map<String, String> getBucketInfo() { return this.bucketInfo; }

	/**
	 * Get the revision number for the bucket
	 * 
	 * @return the revision number for the bucket
	 */
	public long getRevision() { return this.revision; }

	/**
	 * Return the list of all of the lifecycle rules that apply to this bucket
	 * 
	 * @return the list of all lifecycle rules
	 */
	public List<LifecycleRule> getLifecycleRules() { return lifecycleRules; }

	@Override
	protected Logger getLogger() { return LOGGER; }

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
