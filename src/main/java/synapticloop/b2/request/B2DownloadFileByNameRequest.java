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

import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.util.Helper;

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

	public B2DownloadFileByNameRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, String fileName) {
		super(client, 
				b2AuthorizeAccountResponse, 
				b2AuthorizeAccountResponse.getDownloadUrl() + "/file/" + Helper.urlEncode(bucketName) + "/" + Helper.urlEncodeFileName(fileName));
	}

	public B2DownloadFileByNameRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, String fileName, long rangeStart, long rangeEnd) {
		this(client, b2AuthorizeAccountResponse, bucketName, fileName);
		requestHeaders.put(HttpHeaders.RANGE, "bytes=" + rangeStart + "-" + rangeEnd);
	}

	/**
	 * Execute the request and return the response
	 * 
	 * @return The download file response
	 * 
	 * @throws B2Exception If there was an error with the call
	 */
	public B2DownloadFileResponse getResponse() throws B2Exception {
		return(new B2DownloadFileResponse(executeGet()));
	}
}
