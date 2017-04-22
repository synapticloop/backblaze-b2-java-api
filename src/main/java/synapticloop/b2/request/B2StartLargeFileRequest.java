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
import java.util.Map;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ResponseHeaders;
import synapticloop.b2.response.B2StartLargeFileResponse;
import synapticloop.b2.util.URLEncoder;

public class B2StartLargeFileRequest extends BaseB2Request {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2StartLargeFileRequest.class);

	private static final String B2_START_LARGE_FILE = BASE_API_VERSION + "b2_start_large_file";
	protected static final String CONTENT_TYPE_VALUE_B2_X_AUTO = "b2/x-auto";

	/**
	 * Create a new B2 Start large file request
	 *
	 * @param client the HTTP client to use
	 * @param b2AuthorizeAccountResponse the authorise account response
	 * @param bucketId                   the id of the bucket to upload to
	 * @param fileName                   the name of the file
	 * @param mimeType                   the mimeType (optional, will default to 'b2/x-auto' which
	 *                                   backblaze will attempt to determine automatically)
	 * @param fileInfo                   the file info map which are passed through as key value
	 *                                   pairs in a jsonObject named 'fileInfo'
	 *
	 * @throws B2ApiException if something went wrong
	 */
	public B2StartLargeFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
								   String bucketId, String fileName,
								   String mimeType, Map<String, String> fileInfo) throws B2ApiException {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_START_LARGE_FILE);

		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		this.addProperty(B2RequestProperties.KEY_FILE_NAME, fileName);
		if(null == mimeType) {
			this.addProperty(B2RequestProperties.KEY_CONTENT_TYPE, CONTENT_TYPE_VALUE_B2_X_AUTO);
		} else {
			this.addProperty(B2RequestProperties.KEY_CONTENT_TYPE, mimeType);
		}

		// Add 'X-Bz-Info-*' headers
		if (null != fileInfo) {
			for (final String key : fileInfo.keySet()) {
				this.addHeader(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX + URLEncoder.encode(key), URLEncoder.encode(fileInfo.get(key)));
			}
		}
	}

	/**
	 * Return the start large file response 
	 * 
	 * @return the start large file response
	 * 
	 * @throws B2ApiException if something went wrong
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2StartLargeFileResponse getResponse() throws B2ApiException, IOException {
		return new B2StartLargeFileResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
