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

import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file from B2.</p>
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * <ul>
 * <li>Content-Length</li>
 * <li>Content-Type</li>
 * <li>X-Bz-File-Id</li>
 * <li>X-Bz-File-Name</li>
 * <li>X-Bz-Content-Sha1</li>
 * <li>X-Bz-Info-*</li>
 * </ul>
 * 
 * <p>HEAD requests are also supported, and work just like a GET, except that the
 * body of the response is not included. All of the same headers, including
 * Content-Length are returned. See the B2HeadFileByIdRequest</p>
 * 
 * <p>If the bucket containing the file is set to require authorization, then you
 * must supply the bucket's auth token in the Authorzation header.</p>
 * 
 * <p>Because errors can happen in network transmission, you should check the
 * SHA1 of the data you receive against the SHA1 returned in the
 * X-Bz-Content-Sha1 header.</p>
 * 
 * This is the interaction class for the <strong>b2_download_file_by_id</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_id.html">http://www.backblaze.com/b2/docs/b2_download_file_by_id.html</a>
 *
 * @author synapticloop
 */
public class B2DownloadFileByIdRequest extends BaseB2Request {
	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";

	/**
	 * Create a download file by ID request to download the complete file
	 *
	 * @param client                     Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param fileId                     the unique id of the file
	 */
	public B2DownloadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		this(client, b2AuthorizeAccountResponse, fileId, -1, -1);
	}

	/**
	 * <p>Create a download file by ID request to download the range of bytes
	 * between rangeStart and rangeEnd (inclusive)
	 * </p>
	 * <p>
	 * A standard byte-range request, which will return just part of the stored file.
	 * </p>
	 * <p>
	 * The value "bytes=0-99" selects bytes 0 through 99 (inclusive) of the file,
	 * so it will return the first 100 bytes. Valid byte ranges will cause the
	 * response to contain a Content-Range header that specifies which bytes are
	 * returned. Invalid byte ranges will just return the whole file.
	 * </p>
	 * <p>
	 * Note that the SHA1 checksum returned is still the checksum for the entire
	 * file, so it cannot be used on the byte range.
	 * </p>
	 *
	 * @param client                     Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param fileId                     the unique id of the file
	 * @param rangeStart                 the start of the range. -1 to read from start of file
	 * @param rangeEnd                   the end of the range. -1 to read until EOF
	 */
	public B2DownloadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
									 String fileId, long rangeStart, long rangeEnd) {

		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID);
		this.addParameter(B2RequestProperties.KEY_FILE_ID, fileId);
		if (rangeStart > -1) {
			if (rangeEnd > -1) {
				this.addHeader(HttpHeaders.RANGE, String.format("bytes=%d-%d", rangeStart, rangeEnd));
			} else {
				this.addHeader(HttpHeaders.RANGE, String.format("bytes=%d-", rangeStart));
			}
		}
	}

	/**
	 * Execute the request and return the response
	 *
	 * @return The download file response
	 * 
	 * @throws B2ApiException If there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse getResponse() throws B2ApiException, IOException {
		return new B2DownloadFileResponse(executeGet());
	}
}
