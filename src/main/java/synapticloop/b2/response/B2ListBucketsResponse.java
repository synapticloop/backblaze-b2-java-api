package synapticloop.b2.response;

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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public final class B2ListBucketsResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2ListBucketsResponse.class);

	private final List<B2BucketResponse> buckets;

	/**
	 * Instantiate a list bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2ListBucketsResponse(final String json) throws B2ApiException {
		super(json);

		buckets = new ArrayList<>();
		JSONArray optJSONArray = this.readObjects(B2ResponseProperties.KEY_BUCKETS);
		for(int i = 0; i < optJSONArray.length(); i++) {
			buckets.add(new B2BucketResponse(optJSONArray.optJSONObject(i)));
		}

		this.warnOnMissedKeys();
	}

	/**
	 * Return a list of all of the buckets
	 * 
	 * @return the bucket list (but not the film version :))
	 */
	public List<B2BucketResponse> getBuckets() {
		return buckets;
	}

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2ListBucketsResponse{");
		sb.append("buckets=").append(buckets);
		sb.append('}');
		return sb.toString();
	}
}
