package synapticloop.b2.request;

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

import org.apache.http.impl.client.CloseableHttpClient;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file from B2.</p>
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_id</strong> 
 * api calls, this was generated from the backblaze api documentation - which 
 * can be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_id.html">http://www.backblaze.com/b2/docs/b2_download_file_by_id.html</a>
 * 
 * @author synapticloop
 */
public class B2HeadFileByIdRequest extends BaseB2Request {
	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";

	public B2HeadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID);
		requestParameters.put(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Return the response for the HEAD request 
	 * 
	 * @return the download file response - note that this does not contain any body content
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public B2DownloadFileResponse getResponse() throws B2ApiException {
		return(new B2DownloadFileResponse(this.executeHead()));
	}
}
