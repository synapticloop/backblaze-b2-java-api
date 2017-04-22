package synapticloop.b2.response;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
 * Copyright (c) 2016 iterate GmbH.
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

import synapticloop.b2.exception.B2ApiException;

public class B2GetUploadPartUrlResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2GetUploadPartUrlResponse.class);

	private final String fileId;
	private final String uploadUrl;
	private final String authorizationToken;

	/**
	 * Instantiate a get upload URL response with the JSON response as a string from
	 * the API call.  This response is then parsed into the relevant fields.
	 *
	 * @param json The response (in JSON format)
	 *
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2GetUploadPartUrlResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.uploadUrl = this.readString(B2ResponseProperties.KEY_UPLOAD_URL);
		this.authorizationToken = this.readString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);

		this.warnOnMissedKeys();
	}

	/**
	 * Get the bucket id
	 * 
	 * @return the id of the bucket
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the URL to be used for uploading this file
	 * 
	 * @return the URL to be used to upload this file
	 */
	public String getUploadUrl() { return this.uploadUrl; }
	
	/**
	 * Get the authorization token to be used with the file upload
	 * 
	 * @return the authorization token to be used with the file upload
	 */
	public String getAuthorizationToken() { return this.authorizationToken; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2GetUploadUrlResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", uploadUrl='").append(uploadUrl).append('\'');
		sb.append(", authorizationToken='").append(authorizationToken).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
