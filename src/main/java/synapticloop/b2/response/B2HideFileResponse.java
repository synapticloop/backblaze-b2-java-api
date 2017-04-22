package synapticloop.b2.response;

import java.util.Map;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

public class B2HideFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2HideFileResponse.class);

	private final String fileId;
	private final String fileName;
	private Action action;
	private final Integer size;
	private final Long uploadTimestamp;

	private final Long contentLength;
	private final String contentType;
	private final String contentSha1;
	private final Map<String, String> fileInfo;

  /**
	 * Instantiate a hide file response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2HideFileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = this.readString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = this.readString(B2ResponseProperties.KEY_FILE_NAME);

		final String action = this.readString(B2ResponseProperties.KEY_ACTION);
		if(null != action) {
			try {
				this.action = Action.valueOf(action);
			}
			catch(IllegalArgumentException e) {
				LOGGER.warn("Unknown action value " + action);
				this.action = null;
			}
		} else {
			this.action = null;
		}

		this.size = this.readInt(B2ResponseProperties.KEY_SIZE);
		this.uploadTimestamp = this.readLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);
		this.contentLength = this.readLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentType =this.readString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.contentSha1 =this.readString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.fileInfo = this.readMap(B2ResponseProperties.KEY_FILE_INFO);

		this.warnOnMissedKeys();
	}

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public Action getAction() { return this.action; }

	public Integer getSize() { return this.size; }

	public Long getUploadTimestamp() { return this.uploadTimestamp; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2HideFileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", action=").append(action);
		sb.append(", size=").append(size);
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Get the content length of the downloaded file
	 * 
	 * @return the length of the content
	 */
	public Long getContentLength() { return this.contentLength; }

	/**
	 * Get the content type of the downloaded file
	 * 
	 * @return the content type of the downloaded file
	 */
	public String getContentType() { return this.contentType; }

	/**
	 * Get the SHA1 of the returned content
	 * 
	 * @return the SHA1 of the returned content
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get the file info for the file, this will be returned in the map as 
	 * key(tag), value (super-secret-tag)
	 * 
	 * @return The map of the file info 
	 */
	public Map<String, String> getFileInfo() { return this.fileInfo; }

}
