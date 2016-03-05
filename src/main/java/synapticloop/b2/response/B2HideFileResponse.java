package synapticloop.b2.response;

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

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

public class B2HideFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2HideFileResponse.class);

	private final String fileId;
	private final String fileName;
	private final Action action;
	private final int size;
	private final long uploadTimestamp;

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

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);

		if(null != response.optString(B2ResponseProperties.KEY_ACTION, null)) {
			this.action = Action.valueOf(response.optString(B2ResponseProperties.KEY_ACTION, null));
		} else {
			this.action = null;
		}

		this.size = response.optInt(B2ResponseProperties.KEY_SIZE, -1);
		this.uploadTimestamp = response.optLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP, -1l);

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_FILE_ID);
			response.remove(B2ResponseProperties.KEY_FILE_NAME);
			response.remove(B2ResponseProperties.KEY_ACTION);
			response.remove(B2ResponseProperties.KEY_SIZE);
			response.remove(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);

			warnOnMissedKeys(LOGGER, response);
		}

	}

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public Action getAction() { return this.action; }

	public int getSize() { return this.size; }

	public long getUploadTimestamp() { return this.uploadTimestamp; }

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
}
