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

import synapticloop.b2.exception.B2Exception;

public class B2DeleteFileVersionResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DeleteFileVersionResponse.class);

	private final String fileId;
	private final String fileName;

	public B2DeleteFileVersionResponse(String json) throws B2Exception {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_FILE_ID);
			response.remove(B2ResponseProperties.KEY_FILE_NAME);

			warnOnMissedKeys(LOGGER, response);
		}
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2DeleteFileVersionResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
