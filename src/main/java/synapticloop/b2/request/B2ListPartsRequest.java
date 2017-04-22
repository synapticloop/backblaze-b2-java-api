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
import synapticloop.b2.response.B2ListPartsResponse;

public class B2ListPartsRequest extends BaseB2Request {
	private static final String B2_LIST_PARTS = BASE_API_VERSION + "b2_list_parts";

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId                     The ID of the file, as returned by {@link B2StartLargeFileRequest}
	 */
	public B2ListPartsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
							  String fileId) {
		this(client, b2AuthorizeAccountResponse, fileId, null, null);
	}

	/**
	 * @param client                     The http client to use
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileId                     The ID of the file, as returned by {@link B2StartLargeFileRequest}
	 * @param startPartNumber            The first part to return. If there is an part with this number, it will be
	 *                                   returned in the list. If not, the first upload after this the first one after this number.
	 * @param maxPartCount               The maximum number of parts to return from this call. The default value is 100, and the maximum allowed is 1000.
	 */
	public B2ListPartsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
							  String fileId, Integer startPartNumber, Integer maxPartCount) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_PARTS);

		this.addProperty(B2RequestProperties.KEY_FILE_ID, fileId);
		if (startPartNumber != null) {
			this.addProperty(B2RequestProperties.KEY_START_PART_NUMBER, startPartNumber);
		}
		if (startPartNumber != null) {
			this.addProperty(B2RequestProperties.KEY_MAX_PART_COUNT, maxPartCount);
		}
	}

	public B2ListPartsResponse getResponse() throws B2ApiException, IOException {
		return new B2ListPartsResponse(EntityUtils.toString(executePost().getEntity()));
	}
}
