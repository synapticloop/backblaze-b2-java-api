package synapticloop.b2.request;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
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

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;

public class B2ListUnfinishedLargeFilesRequest extends BaseB2Request {
	private static final String B2_LIST_UNFINISHED_LARGE_FILES = BASE_API_VERSION + "b2_list_unfinished_large_files";

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId                   The ID of the bucket
	 */
	public B2ListUnfinishedLargeFilesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
											 String bucketId) {
		this(client, b2AuthorizeAccountResponse, bucketId, null, null);
	}

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param bucketId                   The ID of the bucket
	 * @param startFileId                The first upload to return. If there is an upload with this ID, it will be returned in
	 *                                   the list. If not, the first upload after this the first one after this ID.
	 * @param maxFileCount               The maximum number of files to return
	 */
	public B2ListUnfinishedLargeFilesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
											 String bucketId, String startFileId, Integer maxFileCount) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_UNFINISHED_LARGE_FILES);

		this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		if (null != startFileId) {
			this.addProperty(B2RequestProperties.KEY_START_FILE_ID, startFileId);
		}
		if (maxFileCount != null) {
			this.addProperty(B2RequestProperties.KEY_MAX_FILE_COUNT, maxFileCount);
		}
	}

	public B2ListFilesResponse getResponse() throws B2ApiException, IOException {
		return new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
