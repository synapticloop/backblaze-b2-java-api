package synapticloop.b2.response;

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

public class B2GetUploadUrlResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2GetUploadUrlResponse.class);

	private final String bucketId;
	private final String uploadUrl;
	private final String authorizationToken;

	public B2GetUploadUrlResponse(String json) throws B2ApiException {
		super(json);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.uploadUrl = response.optString(B2ResponseProperties.KEY_UPLOAD_URL, null);
		this.authorizationToken = response.optString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN, null);

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_BUCKET_ID);
			response.remove(B2ResponseProperties.KEY_UPLOAD_URL);
			response.remove(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);

			warnOnMissedKeys(LOGGER, response);
		}

	}

	public String getBucketId() { return this.bucketId; }
	public String getUploadUrl() { return this.uploadUrl; }
	public String getAuthorizationToken() { return this.authorizationToken; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2GetUploadUrlResponse{");
		sb.append("bucketId='").append(bucketId).append('\'');
		sb.append(", uploadUrl='").append(uploadUrl).append('\'');
		sb.append(", authorizationToken='").append(authorizationToken).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
