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

/*
 * Copyright (c) 2016 iterate GmbH.
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
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2GetUploadPartUrlResponse;

public class B2GetUploadPartUrlRequest extends BaseB2Request {
	private static final String B2_GET_UPLOAD_URL = BASE_API_VERSION + "b2_get_upload_part_url";

	/**
	 * Instantiate a get upload URL request
	 *
	 * @param client the HTTP client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId The ID of the large file whose parts you want to upload
	 */
	public B2GetUploadPartUrlRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
									 String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL);

		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Return the upload url response 
	 * 
	 * @return the upload url response
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2GetUploadPartUrlResponse getResponse() throws B2ApiException, IOException {
		return new B2GetUploadPartUrlResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
