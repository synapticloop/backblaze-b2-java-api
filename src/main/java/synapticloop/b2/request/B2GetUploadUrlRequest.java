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

import java.io.IOException;
import java.util.Map;

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

	public B2GetUploadUrlRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL);

<<<<<<< HEAD
		requestBodyStringData.put(KEY_BUCKET_ID, bucketId);
	}

	public B2GetUploadUrlRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Map<String, String> fileInfo) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL;

		requestBodyStringData.put(KEY_BUCKET_ID, bucketId);
=======
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
	}

	public B2GetUploadUrlRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Map<String, String> fileInfo) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL);
>>>>>>> master

		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
	}

	/**
	 * Return the upload url response 
	 * 
	 * @return the upload url response
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public B2GetUploadUrlResponse getResponse() throws B2ApiException {
		try {
			return(new B2GetUploadUrlResponse(EntityUtils.toString(executePost().getEntity())));
		} catch(IOException e) {
			throw new B2ApiException(e);
		}
	}
}
