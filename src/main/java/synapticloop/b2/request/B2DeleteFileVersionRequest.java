package synapticloop.b2.request;

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

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;

/**
 * <p>Deletes one version of a file from B2.</p>
 * 
 * <p>If the version you delete is the latest version, and there are older 
 * versions, then the most recent older version will become the current 
 * version, and be the one that you'll get when downloading by name. See 
 * the <a href="https://www.backblaze.com/b2/docs/file_versions.html">File Versions</a> page for more details.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_delete_file_version</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_delete_file_version.html">http://www.backblaze.com/b2/docs/b2_delete_file_version.html</a>
 * 
 * @author synapticloop
 */
public class B2DeleteFileVersionRequest extends BaseB2Request {
	private static final String B2_DELETE_FILE_VERSION = BASE_API_VERSION + "b2_delete_file_version";

	/**
	 * Instantiate a delete file version request
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileName the name (and path) of the file to delete
	 * @param fileId The ID of the file, as returned by {@link B2UploadFileRequest},
	 *    {@link B2ListFileNamesRequest}, or {@link B2ListFileVersionsRequest}.
	 */
	public B2DeleteFileVersionRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_DELETE_FILE_VERSION);

		this.addProperty(B2RequestProperties.KEY_FILE_NAME, fileName);
		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Return the response for the call
	 * 
	 * @return the delete file version response
	 * 
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DeleteFileVersionResponse getResponse() throws B2ApiException, IOException {
		return new B2DeleteFileVersionResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
