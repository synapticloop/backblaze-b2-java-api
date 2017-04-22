package synapticloop.b2.request;

import java.io.IOException;

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

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;

/**
 * <p>Lists the names of all files in a bucket, starting at a given name.</p>
 * 
 * <p>This call returns at most 1000 file names, but it can be called repeatedly to scan through all of the file names in a bucket. Each time you call, it returns an "endFileName" that can be used as the starting point for the next call.</p>
 * <p>There may be many file versions for the same name, but this call will return each name only once. If you want all of the versions, use b2_list_file_versions instead.</p>
 * 
 * <p>This is the interaction class for the <strong>b2_list_file_names</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:</p>
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_list_file_names.html">http://www.backblaze.com/b2/docs/b2_list_file_names.html</a>
 *
 * @author synapticloop
 */
public class B2ListFileNamesRequest extends BaseB2Request {
    private static final String B2_LIST_FILE_NAMES = BASE_API_VERSION + "b2_list_file_versions";

    private static final int DEFAULT_MAX_FILE_COUNT = 100;

    /**
     * Create a list file names request, this will return at most the default
     * number of files, which is 100.
     *
     * @param client                     the HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   the id of the bucket to list
     */
    public B2ListFileNamesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
        this(client, b2AuthorizeAccountResponse, bucketId, null, DEFAULT_MAX_FILE_COUNT, null, null);
    }

    /**
     * Create a list file names request returning up to the maxFileCount number of results
     *
     * @param client                     the HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   the id of the bucket to list
     * @param maxFileCount               The maximum number of files to return from this call.
     *                                   The default value is 100, and the maximum allowed is 1000.
     */
    public B2ListFileNamesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Integer maxFileCount) {
        this(client, b2AuthorizeAccountResponse, bucketId, null, maxFileCount, null, null);
    }

    /**
     * Create a list file names request returning up to the maxFileCount number of results
     *
     * @param client                     the HTTP client to use
     * @param b2AuthorizeAccountResponse the authorize account response
     * @param bucketId                   the id of the bucket to list
     * @param startFileName              The first file name to return. If there is a file
     *                                   with this name, it will be returned in the list. If not, the first
     *                                   file name after this the first one after this name.
     * @param maxFileCount               The maximum number of files to return from this call.
     * @param prefix                     Files returned will be limited to those with the given prefix. Defaults to the empty string, which matches all files.
     * @param delimiter                  Files returned will be limited to those within the top folder, or any one subfolder. Defaults to NULL. Folder names will also be returned. The delimiter character will be used to "break" file names into folders.
     */
    public B2ListFileNamesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String startFileName, Integer maxFileCount, String prefix, String delimiter) {
        super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_FILE_NAMES);

        this.addProperty(B2RequestProperties.KEY_BUCKET_ID, bucketId);

        if(null != startFileName) {
            this.addProperty(B2RequestProperties.KEY_START_FILE_NAME, startFileName);
        }
        if(null != prefix) {
            this.addProperty(B2RequestProperties.KEY_PREFIX, prefix);
        }
        if(null != delimiter) {
            this.addProperty(B2RequestProperties.KEY_DELIMITER, delimiter);
        }

        this.addProperty(B2RequestProperties.KEY_MAX_FILE_COUNT, maxFileCount);
    }

    /**
     * Return the list file names response
     *
     * @return the list file names response
     * @throws B2ApiException if something went wrong
     * @throws IOException    if there was an error communicating with the API service
     */
    public B2ListFilesResponse getResponse() throws B2ApiException, IOException {
        return new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity()));
    }
}
