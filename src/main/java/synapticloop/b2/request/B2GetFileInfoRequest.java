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
import synapticloop.b2.response.B2FileResponse;

/**
 * <p>Gets information about one file stored in B2.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_get_file_info</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_get_file_info.html">http://www.backblaze.com/b2/docs/b2_get_file_info.html</a>
 * 
 * @author synapticloop
 */
public class B2GetFileInfoRequest extends BaseB2Request {
	private static final String B2_GET_FILE_INFO = BASE_API_VERSION + "b2_get_file_info";

	/**
	 * Create a new get file info request object
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId The ID of the file, as returned by b2_upload_file, b2_list_file_names, or b2_list_file_versions.
	 * 
	 * {@link B2UploadFileRequest}
	 * {@link B2ListFileNamesRequest}
	 * {@link B2ListFileVersionsRequest}
	 */
	public B2GetFileInfoRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_FILE_INFO);

		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Execute the call and retrieve the response for the get file info
	 * 
	 * @return The details for the file information
	 * 
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2FileResponse getResponse() throws B2ApiException, IOException {
		return new B2FileResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
