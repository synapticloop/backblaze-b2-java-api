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

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2GetUploadPartUrlResponse;
import synapticloop.b2.response.B2ResponseHeaders;
import synapticloop.b2.response.B2UploadPartResponse;

public class B2UploadPartRequest extends BaseB2Request {
	private final HttpEntity entity;

	/**
	 * @param client                     Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse     An upload authorization token, from b2_start_large_file
	 * @param partNumber                 A number from 1 to 10000. The parts uploaded for one file must have contiguous numbers, starting with 1.
	 * @param entity                     the http entity to upload
	 * @param sha1Checksum               The SHA1 checksum of the this part of the file. B2 will check this when the part is
	 *                                   uploaded, to make sure that the data arrived correctly.
	 */
	public B2UploadPartRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
							   B2GetUploadPartUrlResponse b2GetUploadUrlResponse,
							   int partNumber, HttpEntity entity, String sha1Checksum) {
		super(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse.getUploadUrl());
		this.entity = entity;

		this.addHeader(B2ResponseHeaders.HEADER_X_BZ_PART_NUMBER, String.valueOf(partNumber));
		this.addHeader(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1, sha1Checksum);

		// Override generic authorization header
		this.addHeader(HttpHeaders.AUTHORIZATION, b2GetUploadUrlResponse.getAuthorizationToken());
	}

	public B2UploadPartResponse getResponse() throws B2ApiException, IOException {
		return new B2UploadPartResponse(EntityUtils.toString(executePost(entity).getEntity()));
	}
}
