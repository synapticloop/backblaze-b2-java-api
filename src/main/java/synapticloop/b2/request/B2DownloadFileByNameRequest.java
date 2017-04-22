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
import synapticloop.b2.util.URLEncoder;

/**
 * <p>Downloads one file by providing the name of the bucket and the name of the file.</p>
 * 
 * <p>The base URL to use comes from the b2_authorize_account call, and looks something like https://f345.backblaze.com. The "f" in the URL stands for "file", and the number is the cluster number that your account is in. To this base, you add your bucket name, a "/", and then the name of the file. The file name may itself include more "/" characters.</p>
 * <p>If you have a bucket named "photos", and a file called "cute/kitten.jpg", then the URL for downloading that file would be: https://f345.backblaze.com/file/photos/cute/kitten.jpg.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_name</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_name.html">http://www.backblaze.com/b2/docs/b2_download_file_by_name.html</a>
 * 
 * @author synapticloop
 */
public class B2DownloadFileByNameRequest extends BaseB2Request {

	/**
	 * Create a download file by name request
	 * 
	 * @param client The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketName the name of the bucket
	 * @param fileName the name and path of the file
	 */
	public B2DownloadFileByNameRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, String fileName) {
		this(client, b2AuthorizeAccountResponse, bucketName, fileName, -1, -1);
	}

	/**
	 * Create a download file by name request with a specified range
	 *
	 * A standard byte-range request, which will return just part of the stored file.
	 *
	 * The value "bytes=0-99" selects bytes 0 through 99 (inclusive) of the file,
	 * so it will return the first 100 bytes. Valid byte ranges will cause the
	 * response to contain a Content-Range header that specifies which bytes
	 * are returned. Invalid byte ranges will just return the whole file.
	 *
	 * Note that the SHA1 checksum returned is still the checksum for the entire
	 * file, so it cannot be used on the byte range.
	 *
	 * @param client The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketName the name of the bucket
	 * @param fileName the name and path of the file
	 * @param rangeStart the range start of the partial file contents. -1 to read from start of file
	 * @param rangeEnd the range end of the partial file contents. -1 to read from start of file
	 */
	public B2DownloadFileByNameRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, String fileName, long rangeStart, long rangeEnd) {
		super(client,
				b2AuthorizeAccountResponse,
				b2AuthorizeAccountResponse.getDownloadUrl() + "/file/" + URLEncoder.encode(bucketName) + "/" + URLEncoder.encode(fileName));
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
