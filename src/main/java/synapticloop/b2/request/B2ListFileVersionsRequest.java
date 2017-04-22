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

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;

/**
 * <p>Lists all of the versions of all of the files contained in one bucket, in
 * alphabetical order by file name, and by reverse of date/time uploaded for
 * versions of files with the same name.</p>
 * <p>
 * This is the interaction class for the <strong>b2_list_file_versions</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <p>
 * <a href="http://www.backblaze.com/b2/docs/b2_list_file_versions.html">http://www.backblaze.com/b2/docs/b2_list_file_versions.html</a>
 *
 * @author synapticloop
 */
public class B2ListFileVersionsRequest extends BaseB2Request {
    private static final String B2_LIST_FILE_VERSIONS = BASE_API_VERSION + "b2_list_file_versions";

    private static final int DEFAULT_MAX_FILE_COUNT = 100;

    /**
     * Create a list files request
     *
     * @param client                     The HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   The id of the bucket to look for file names in.
     */
    public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                                     String bucketId) {
        this(client, b2AuthorizeAccountResponse, bucketId, DEFAULT_MAX_FILE_COUNT);
    }

    /**
     * Create a list files request
     *
     * @param client                     The HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   The id of the bucket to look for file names in.
     * @param maxFileCount               The maximum number of files to return from this call.
     *                                   The default value is 100, and the maximum allowed is 1000.
     */
    public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                                     String bucketId, Integer maxFileCount) {
        this(client, b2AuthorizeAccountResponse, bucketId, maxFileCount, null, null, null, null);
    }

    /**
     * Create a list files request
     *
     * @param client                     The HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   The id of the bucket to look for file names in.
     * @param maxFileCount               The maximum number of files to return from this call.
     *                                   The default value is 100, and the maximum allowed is 1000.
     * @param startFileName              The first file name to return.  If there are no files
     *                                   with this name, the first version of the file with the first name after
     *                                   the given name will be the first in the list.  If startFileId is also
     *                                   specified, the name-and-id pair is the starting point. If there is a
     *                                   file with the given name and ID, it will be first in the list.
     *                                   Otherwise, the first file version that comes after the given name and
     *                                   ID will be first in the list.
     * @param startFileId                The first file ID to return. startFileName must also
     * @param prefix                     Files returned will be limited to those with the given prefix. Defaults to the empty string, which matches all files.
     * @param delimiter                  Files returned will be limited to those within the top folder, or any one subfolder. Defaults to NULL. Folder names will also be returned. The delimiter character will be used to "break" file names into folders.
     */
    public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
                                     String bucketId, Integer maxFileCount, String startFileName, String startFileId,
                                     String prefix, String delimiter) {
        super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_FILE_VERSIONS);

        this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);
        this.addProperty(B2RequestProperties.KEY_MAX_FILE_COUNT, maxFileCount);
        if(null != startFileName) {
            this.addProperty(B2RequestProperties.KEY_START_FILE_NAME, startFileName);
        }
        if(null != startFileId) {
            this.addProperty(B2RequestProperties.KEY_START_FILE_ID, startFileId);
        }
        if(null != prefix) {
            this.addProperty(B2RequestProperties.KEY_PREFIX, prefix);
        }
        if(null != delimiter) {
            this.addProperty(B2RequestProperties.KEY_DELIMITER, delimiter);
        }
    }

    /**
     * Return the list file versions response
     *
     * @return the list file versions response
     * @throws B2ApiException if something went wrong
     * @throws IOException    if there was an error communicating with the API service
     */
    public B2ListFilesResponse getResponse() throws B2ApiException, IOException {
        return new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity()));
    }
}
