package synapticloop.b2.request;

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
import synapticloop.b2.response.B2ListFilesResponse;

import java.io.IOException;

public class B2ListUnfinishedLargeFilesRequest extends BaseB2Request {
	private static final String B2_LIST_UNFINISHED_LARGE_FILES = BASE_API_VERSION + "b2_list_unfinished_large_files";

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId                   The ID of the bucket
	 */
	public B2ListUnfinishedLargeFilesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
											 String bucketId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_UNFINISHED_LARGE_FILES);

		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
	}

	public B2ListFilesResponse getResponse() throws B2ApiException, IOException {
		return new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
