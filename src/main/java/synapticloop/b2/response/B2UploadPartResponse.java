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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2UploadPartResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2UploadPartResponse.class);

	private final String fileId;
	private final Integer partNumber;
	private final Long contentLength;
	private final String contentSha1;
	private final Long uploadTimestamp;

	public B2UploadPartResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.partNumber = this.readInt(B2ResponseProperties.KEY_PART_NUMBER);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.uploadTimestamp = this.readLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);

		this.warnOnMissedKeys();
	}

	public B2UploadPartResponse(final JSONObject response) throws B2ApiException {
		super(response);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.partNumber = this.readInt(B2ResponseProperties.KEY_PART_NUMBER);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.uploadTimestamp = this.readLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);

		this.warnOnMissedKeys();
	}

	public String getFileId() {
		return fileId;
	}

	public Integer getPartNumber() {
		return partNumber;
	}

	public Long getContentLength() {
		return contentLength;
	}

	public String getContentSha1() {
		return contentSha1;
	}

	/**
	 * Return the timestamp that the part was uploaded
	 *
	 * @return the timestamp for when the part was uploaded
	 */
	public Long getUploadTimestamp() { return this.uploadTimestamp; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2UploadPartResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", partNumber=").append(partNumber);
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
