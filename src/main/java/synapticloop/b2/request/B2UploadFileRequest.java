package synapticloop.b2.request;

/*
 * Copyright (c) 2016 synapticloop.
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

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2ResponseHeaders;
import synapticloop.b2.util.Helper;

/**
 * <p>Uploads one file to B2, returning its unique file ID.</p>
 *
 *
 * This is the interaction class for the <strong>b2_upload_file</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_upload_file.html">http://www.backblaze.com/b2/docs/b2_upload_file.html</a>
 *
 * @author synapticloop
 */
public class B2UploadFileRequest extends BaseB2Request {
	private final HttpEntity entity;
	private final String mimeType;
	private final String fileName;

	protected static final String CONTENT_TYPE_VALUE_B2_X_AUTO = "b2/x-auto";

	/**
	 * Instantiate a upload file request in order to place a file on the B2 bucket,
	 * the sha1 sum will be automatically generated
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse the upload URL for this request
	 * @param fileName the name of the file
	 * @param file the file to upload
	 * @param fileInfo the file info map which are passed through as headers prefixed by "X-Bz-Info-"
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, Map<String, String> fileInfo) throws B2Exception {

		this(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file, null, fileInfo);
	}

	/**
	 * Instantiate a upload file request in order to place a file on the B2 bucket,
	 * the sha1 sum will be automatically generated.
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse the upload URL for this request
	 * @param fileName the name of the file
	 * @param file the file to upload
	 * @param mimeType the mimeTyp (optional, will default to 'b2/x-auto' which
	 *     backblaze will attempt to determine automatically)
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType) throws B2Exception {

		this(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file, mimeType, null);
	}

	/**
	 * Instantiate a upload file request in order to place a file on the B2 bucket,
	 * the sha1 sum will be automatically generated.
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse the upload URL for this request
	 * @param fileName the name of the file
	 * @param file the file to upload
	 */
	public B2UploadFileRequest(CloseableHttpClient client, 
			B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
			B2GetUploadUrlResponse b2GetUploadUrlResponse, 
			String fileName, 
			File file) throws B2Exception {

		this(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file, null, null);
	}

	/**
	 * Instantiate a upload file request in order to place a file on the B2 bucket,
	 * the sha1 sum will be automatically generated.
	 *
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse the upload URL for this request
	 * @param fileName the name of the file
	 * @param file the file to upload
	 * @param mimeType the mimeTyp (optional, will default to 'b2/x-auto' which
	 *     backblaze will attempt to determine automatically)
	 * @param fileInfo the file info map which are passed through as headers
	 *     prefixed by "X-Bz-Info-"
	 */
	public B2UploadFileRequest(CloseableHttpClient client, 
			B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, 
			String fileName, 
			File file, 
			String mimeType, 
			Map<String, String> fileInfo) throws B2Exception {

		this(client, 
				b2AuthorizeAccountResponse, 
				b2GetUploadUrlResponse, 
				fileName, 
				new FileEntity(file),
				Helper.calculateSha1(file),
				mimeType, fileInfo);
	}

	public B2UploadFileRequest(CloseableHttpClient client, 
			B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, 
			String fileName, 
			HttpEntity entity, 
			String sha1Checksum, 
			String mimeType, 
			Map<String, String> fileInfo) {

		super(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse.getUploadUrl());

		this.fileName = fileName;
		this.entity = entity;
		this.mimeType = mimeType;

		// now go through and add in the 'X-Bz-Info-*' headers
		if(null != fileInfo) {
			for(final String key : fileInfo.keySet()) {
				requestHeaders.put(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX + Helper.urlEncode(key), Helper.urlEncode(fileInfo.get(key)));
			}
		}
		requestHeaders.put(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1, sha1Checksum);
		// Override generic authorization header
		requestHeaders.put(HttpHeaders.AUTHORIZATION, b2GetUploadUrlResponse.getAuthorizationToken());
	}

	/**
	 * Return the file response 
	 * 
	 * @return the file response
	 * 
	 * @throws B2Exception if something went wrong
	 */

	public B2FileResponse getResponse() throws B2Exception {
		if(null == mimeType) {
			requestHeaders.put(B2ResponseHeaders.HEADER_CONTENT_TYPE, CONTENT_TYPE_VALUE_B2_X_AUTO);
		} else {
			requestHeaders.put(B2ResponseHeaders.HEADER_CONTENT_TYPE, mimeType);
		}

		requestHeaders.put(B2ResponseHeaders.HEADER_X_BZ_FILE_NAME, Helper.urlEncode(fileName));

		try {
			return new B2FileResponse(EntityUtils.toString(executePost(entity).getEntity()));
		} catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
