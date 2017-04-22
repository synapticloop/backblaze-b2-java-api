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

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Gets information on one file from B2.</p>
 * 
 * <p><strong>NO</strong> file content is returned with this request
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * <ul>
 *   <li>Content-Length</li>
 *   <li>Content-Type</li>
 *   <li>X-Bz-File-Id</li>
 *   <li>X-Bz-File-Name</li>
 *   <li>X-Bz-Content-Sha1</li>
 *   <li>X-Bz-Info-*</li>
 * </ul>
 * 
 * <p>HEAD requests are also supported, and work just like a GET, except that the 
 * body of the response is not included. All of the same headers, including 
 * Content-Length are returned. See the B2HeadFileByIdRequest</p>
 * 
 * <p>If the bucket containing the file is set to require authorization, then you 
 * must supply the bucket's auth token in the Authorzation header.</p>
 *  * 
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

	/**
	 * Create a head file by Id request which returns the information about the 
	 * file and any attached file information
	 * 
	 * @param client The HTTPClient to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId the id of the file to request information for
	 */
	public B2HeadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID);
		this.addParameter(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Return the response for the HEAD request 
	 * 
	 * @return the download file response - note that this does not contain any body content
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse getResponse() throws B2ApiException, IOException {
		return new B2DownloadFileResponse(this.executeHead());
	}
}
