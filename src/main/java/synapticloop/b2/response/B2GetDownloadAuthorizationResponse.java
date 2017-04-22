package synapticloop.b2.response;

/*
 * Copyright (c) 2017 Synapticloop.
 * Copyright (c) 2017 iterate GmbH.
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

public class B2GetDownloadAuthorizationResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2AuthorizeAccountResponse.class);

	private final String bucketId;
	private final String fileNamePrefix;
	private final String authorizationToken;

	public B2GetDownloadAuthorizationResponse(String json) throws B2ApiException {
		super(json);

		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.fileNamePrefix = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.authorizationToken = this.readString(B2ResponseProperties.KEY_AUTHORIZATION_TOKEN);

		this.warnOnMissedKeys();
	}

	/**
	 * @return The identifier for the bucket.
	 */
	public String getBucketId() { return bucketId; }

	/**
	 * @return The prefix for files the authorization token will allow b2_download_file_by_name to access.
	 */
	public String getFileNamePrefix() { return fileNamePrefix; }

	/**
	 * @return The authorization token that can be passed in the Authorization header or as an Authorization
	 * parameter to b2_download_file_by_name to access files beginning with the file name prefix.
	 */
	public String getAuthorizationToken() { return authorizationToken; }

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2GetDownloadAuthorizationResponse{");
		sb.append("bucketId='").append(bucketId).append('\'');
		sb.append(", fileNamePrefix='").append(fileNamePrefix).append('\'');
		sb.append(", authorizationToken='").append(authorizationToken).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
