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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2FileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final Long contentLength;
	private final String contentSha1;
	private final String contentType;
	private final Map<String, String> fileInfo;

	@SuppressWarnings("rawtypes")
	public B2FileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.contentLength = response.optLong(B2ResponseProperties.KEY_CONTENT_LENGTH, -1l);
		this.contentSha1 = response.optString(B2ResponseProperties.KEY_CONTENT_SHA1, null);
		this.contentType = response.optString(B2ResponseProperties.KEY_CONTENT_TYPE, null);

		this.fileInfo = new HashMap<String, String>();

		JSONObject fileInfoObject = response.optJSONObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.optString(key, null));
			}
		}

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_FILE_ID);
			response.remove(B2ResponseProperties.KEY_FILE_NAME);
			response.remove(B2ResponseProperties.KEY_ACCOUNT_ID);
			response.remove(B2ResponseProperties.KEY_BUCKET_ID);
			response.remove(B2ResponseProperties.KEY_CONTENT_LENGTH);
			response.remove(B2ResponseProperties.KEY_CONTENT_SHA1);
			response.remove(B2ResponseProperties.KEY_CONTENT_TYPE);
			response.remove(B2ResponseProperties.KEY_FILE_INFO);

			warnOnMissedKeys(LOGGER, response);
		}
	}

	/**
	 * Get the id of the file that was operated on
	 * 
	 * @return the id of the file that was operated on
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file that was operated on
	 * 
	 * @return the name of the file that was operated on
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the id of the account that was operated on
	 * 
	 * @return the id of the account that was operated on
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Get the id of the bucket that was operated on
	 * 
	 * @return the id of the bucket that was operated on
	 */
	public String getBucketId() { return this.bucketId; }

	/**
	 * Get content length of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content length of the file that was operated on
	 */
	public long getContentLength() { return this.contentLength; }

	/**
	 * Get content SHA1 of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content SHA1 of the file that was operated on
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get content type of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content type of the file that was operated on
	 */
	public String getContentType() { return this.contentType; }
<<<<<<< HEAD

	/**
	 * Get the map of the file info for the file that was operated on, or an empty 
	 * map if not set.
	 * 
	 * @return the map of the file info for the file that was operated on
	 */
	public Map<String, Object> getFileInfo() { return this.fileInfo; }
=======
	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append(", bucketId='").append(bucketId).append('\'');
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
>>>>>>> master
}
