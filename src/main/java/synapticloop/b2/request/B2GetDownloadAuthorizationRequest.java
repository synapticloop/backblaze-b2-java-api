package synapticloop.b2.request;

/*
 * Copyright (c) 2017 Synapticloop.
 * Copyright (c) 2017 iterate GmbH.
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
import synapticloop.b2.response.B2GetDownloadAuthorizationResponse;

public class B2GetDownloadAuthorizationRequest extends BaseB2Request {
    private static final String B2_GET_DOWNLOAD_AUTHORIZATION = BASE_API_VERSION + "b2_get_download_authorization";

    /**
     * @param client                     The http client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId The identifier for the bucket.
     * @param fileNamePrefix The file name prefix of files the download authorization token will allow b2_download_file_by_name to access.
     * @param validDurationInSeconds The number of seconds before the authorization token will expire.
     *                               The maximum value is 604800 which is one week in seconds.
     */
    public B2GetDownloadAuthorizationRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                                                String bucketId, String fileNamePrefix, Integer validDurationInSeconds) {
        super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_GET_DOWNLOAD_AUTHORIZATION);

        this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
        this.addProperty(B2RequestProperties.KEY_FILE_NAME_PREFIX, fileNamePrefix);
        this.addProperty(B2RequestProperties.KEY_VALID_DURATION_INSECONDS, validDurationInSeconds);
    }

    public B2GetDownloadAuthorizationResponse getResponse() throws B2ApiException, IOException {
        return new B2GetDownloadAuthorizationResponse(EntityUtils.toString(executePost().getEntity()));
    }
}
