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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2ListFilesResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2ListFilesResponse.class);

	private final List<B2FileInfoResponse> files;
	private final String nextFileName;
	private final String nextFileId;

	/**
	 * Instantiate a list files response with the JSON response as a 
	 * string from the API call.  This response is then parsed into the 
	 * relevant fields.
	 * 
	 * @param json the response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2ListFilesResponse(String json) throws B2ApiException {
		super(json);

		this.nextFileName = this.readString(B2ResponseProperties.KEY_NEXT_FILE_NAME);
		this.nextFileId = this.readString(B2ResponseProperties.KEY_NEXT_FILE_ID);

		JSONArray filesArray = this.readObjects(B2ResponseProperties.KEY_FILES);

		files = new ArrayList<B2FileInfoResponse>();
		for(int i = 0; i < filesArray.length(); i ++) {
			files.add(new B2FileInfoResponse(filesArray.optJSONObject(i)));
		}

		this.warnOnMissedKeys();
	}

	/**
	 * get the next file name that is the next result to be returned after this 
	 * result set - or null if there are no more files
	 * 
	 * @return the next file name to start the next iteration (or null if no next file)
	 */
	public String getNextFileName() { return this.nextFileName; }

	/**
	 * get the next file id that is the next result to be returned after this 
	 * result set - or null if there are no more files
	 * 
	 * @return the next file id to start the next iteration (or null if no next file id)
	 */
	public String getNextFileId() { return this.nextFileId; }

	/**
	 * Return the list of files include file info
	 * 
	 * @return the list of files for this request
	 */
	public List<B2FileInfoResponse> getFiles() { return this.files; }

	@Override
	protected Logger getLogger() { return LOGGER; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2ListFilesResponse{");
		sb.append("files=").append(files);
		sb.append(", nextFileName='").append(nextFileName).append('\'');
		sb.append(", nextFileId='").append(nextFileId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
