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
import synapticloop.b2.response.B2FileResponse;

public class B2CancelLargeFileRequest extends BaseB2Request {
	private static final String B2_CANCEL_LARGE_FILE = BASE_API_VERSION + "b2_cancel_large_file";

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId                     The ID of the file, as returned by {@link B2StartLargeFileRequest},
	 */
	public B2CancelLargeFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
									String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_CANCEL_LARGE_FILE);

		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	public B2FileResponse getResponse() throws B2ApiException, IOException {
		return new B2FileResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
