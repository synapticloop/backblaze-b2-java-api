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

import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

public class B2FileInfoResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileInfoResponse.class);

	private final String fileId;
	private final String fileName;
	private final String contentType;
	private final String contentSha1;
	private final Long contentLength;
	private final String accountId;
	private final String bucketId;

	private final Map<String, String> fileInfo;
	private Action action;
	private final Long size;
	private final Long uploadTimestamp;

	/**
	 * Instantiate a file info response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param response The pre-parsed response as a JSON object
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2FileInfoResponse(final JSONObject response) throws B2ApiException {
		super(response);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentType = this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.contentSha1 = this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.fileInfo = this.readMap(B2ResponseProperties.KEY_FILE_INFO);
		String action = this.readString(B2ResponseProperties.KEY_ACTION);
		if(null != action) {
			try {
				this.action = Action.valueOf(action);
			}
			catch(IllegalArgumentException e) {
				LOGGER.warn("Unknown action value " + action);
			}
		}

		this.size = this.readLong(B2ResponseProperties.KEY_SIZE);
		this.uploadTimestamp = this.readLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);
		this.accountId = this.readString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = this.readString(B2ResponseProperties.KEY_BUCKET_ID);

		this.warnOnMissedKeys();
	}

	/**
	 * Get the fileId that uniquely identifies this file
	 * 
	 * @return the fileId
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file as stored in the backblaze bucket
	 * 
	 * @return the name of the file as stored in the backblaze bucket
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the content length for this file
	 * 
	 * @return the length of content for this file
	 */
	public Long getContentLength() { return this.contentLength; }

	/**
	 * @return the MIME type of the file
	 */
	public String getContentType() { return contentType; }

	/**
	 * Get the sha1 hash of the content - this can be used to verify the 
	 * integrity of the downloaded file
	 * 
	 * @return the sha1 has of the content
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get the file info for the downloaded file - which is a map of key value
	 * pairs with both the key and value being strings
	 * 
	 * @return the file info map of key:value strings
	 */

	public Map<String, String> getFileInfo() { return this.fileInfo; }

	/**
	 * The action that was performed, this will be one of of 'hide', or 'upload'
	 * 
	 * @return The action that was performed
	 */
	public Action getAction() { return this.action; }

	/**
	 * @return The number of bytes in the file.
	 */
	public long getSize() { return this.size; }

	/**
	 * Return the timestamp that the file was uploaded
	 *
	 * @return the timestamp for when the file was uploaded
	 */
	public Long getUploadTimestamp() { return this.uploadTimestamp; }

	/**
	 * Return the account ID used to authorize this account
	 * 
	 * @return the account ID
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Return the bucket ID that this file belongs in
	 * 
	 * @return the bucket ID
	 */
	public String getBucketId() { return this.bucketId; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		return "B2FileInfoResponse " +
				"[fileId=" + this.fileId + 
				", fileName=" + this.fileName + 
				", contentType=" + this.contentType + 
				", contentSha1=" + this.contentSha1 + 
				", contentLength=" + this.contentLength + 
				", accountId=" + this.accountId + 
				", bucketId=" + this.bucketId + 
				", fileInfo=" + this.fileInfo + 
				", action=" + this.action + 
				", size=" + this.size + 
				", uploadTimestamp=" + this.uploadTimestamp + 
				"]";
	}
}
