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


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2StartLargeFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2StartLargeFileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final String contentType;
	private final String uploadTimestamp;

	private final Map<String, String> fileInfo;

	public B2StartLargeFileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);
		this.contentType = this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.fileInfo = this.readMap(B2ResponseProperties.KEY_FILE_INFO);
		this.uploadTimestamp = this.readString(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);

		this.warnOnMissedKeys();
	}

	public String getBucketId() { return this.bucketId; }

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public String getAccountId() { return this.accountId; }

	public String getContentType() { return this.contentType; }

	/**
	 * Get the upload timestamp of the file
	 * 
	 * @return the upload timestamp of the file
	 */
	public String getUploadTimestamp() { return this.uploadTimestamp; }

	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2StartLargeFileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", bucketId='").append(bucketId).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}
