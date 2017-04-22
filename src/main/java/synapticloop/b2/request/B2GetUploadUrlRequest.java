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
import synapticloop.b2.response.B2GetUploadUrlResponse;

/**
 * <p>Gets an URL to use for uploading files.</p>
 * 
 * <p>When you upload a file to B2, you must call b2_get_upload_url first to get the URL for uploading directly to the place where the file will be stored.</p>
 * <p>An uploadUrl and upload authorizationToken are valid for 24 hours or until the endpoint rejects an upload</p>
 *
 * 
 * This is the interaction class for the <strong>b2_get_upload_url</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_get_upload_url.html">http://www.backblaze.com/b2/docs/b2_get_upload_url.html</a>
 * 
 * @author synapticloop
 */
public class B2GetUploadUrlRequest extends BaseB2Request {
	private static final String B2_GET_UPLOAD_URL = BASE_API_VERSION + "b2_get_upload_url";

	/**
	 * Instantiate a get upload URL request
	 * 
	 * @param client the HTTP client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId The ID of the bucket that you want to upload to.
	 */
	public B2GetUploadUrlRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL);

		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
	}

	/**
	 * Return the upload url response 
	 * 
	 * @return the upload url response
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2GetUploadUrlResponse getResponse() throws B2ApiException, IOException {
		return new B2GetUploadUrlResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
