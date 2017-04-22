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
import org.json.JSONArray;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FinishLargeFileResponse;

public class B2FinishLargeFileRequest extends BaseB2Request {
	private static final String B2_FINISH_LARGE_FILE = BASE_API_VERSION + "b2_finish_large_file";

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId                     The ID of the file, as returned by {@link B2StartLargeFileRequest},
	 * @param partSha1Array              A JSON array of hex SHA1 checksums of the parts of the large file. This is a double-check
	 *                                   that the right parts were uploaded in the right order, and that none were missed.
	 *                                   Note that the part numbers start at 1, and the SHA1 of the part 1 is the first string
	 *                                   in the array, at index 0.
	 */
	public B2FinishLargeFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
									String fileId, String[] partSha1Array) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_FINISH_LARGE_FILE);

		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);

		JSONArray checksums = new JSONArray();
		for (String part : partSha1Array) {
			checksums.put(part);
		}
		this.addProperty(B2RequestProperties.KEY_PART_SHA1_ARRAY, checksums);
	}

	public B2FinishLargeFileResponse getResponse() throws B2ApiException, IOException {
		return new B2FinishLargeFileResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
