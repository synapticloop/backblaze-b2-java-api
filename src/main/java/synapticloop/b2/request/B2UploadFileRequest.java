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

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2ResponseHeaders;
import synapticloop.b2.util.ChecksumHelper;
import synapticloop.b2.util.URLEncoder;

/**
 * <p>Uploads one file to B2, returning its unique file ID.</p>
 *
 * <p>The file name and file info must fit, along with the other necessary 
 * headers, within a 7,000 byte limit. This limit applies to the fully encoded 
 * HTTP header line, including the carriage-return and newline. See Files 
 * for further details about HTTP header size limit.</p>
 * 
 * This is the interaction class for the <strong>b2_upload_file</strong> api calls, this was
 * 
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
	 * 
	 * @throws B2ApiException if there was an error in the request
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, Map<String, String> fileInfo) throws B2ApiException {

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
	 * @param mimeType the mimeType (optional, will default to 'b2/x-auto' which
	 *     backblaze will attempt to determine automatically).  The MIME type of 
	 *     the content of the file, which will be returned in the Content-Type 
	 *     header when downloading the file. Use the Content-Type b2/x-auto 
	 *     to automatically set the stored Content-Type post upload. In the case 
	 *     where a file extension is absent or the lookup fails, the Content-Type 
	 *     is set to application/octet-stream.
	 * 
	 * @throws B2ApiException if there was an error in the request
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType) throws B2ApiException {

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
	 * 
	 * @throws B2ApiException if there was an error in the request
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse,
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file) throws B2ApiException {

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
	 * @param mimeType the mimeType (optional, will default to 'b2/x-auto' which
	 *     backblaze will attempt to determine automatically).  The MIME type of 
	 *     the content of the file, which will be returned in the Content-Type 
	 *     header when downloading the file. Use the Content-Type b2/x-auto 
	 *     to automatically set the stored Content-Type post upload. In the case 
	 *     where a file extension is absent or the lookup fails, the Content-Type 
	 *     is set to application/octet-stream.
	 * @param fileInfo the file info map which are passed through as headers
	 *     prefixed by "X-Bz-Info-"
	 * 
	 * @throws B2ApiException if there was an error in the request
	 */
	public B2UploadFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType, 
			Map<String, String> fileInfo) throws B2ApiException {

		this(client, 
				b2AuthorizeAccountResponse, 
				b2GetUploadUrlResponse, 
				fileName, 
				new FileEntity(file),
				ChecksumHelper.calculateSha1(file),
				mimeType, fileInfo);
	}

	/**
	 * Instantiate a upload file request in order to place a file on the B2 bucket,
	 * with a pre-generated sha1 sum, along with the file information that will be
	 * attached to the file.
	 * 
	 * @param client Shared HTTP client instance
	 * @param b2AuthorizeAccountResponse The authorize account response
	 * @param b2GetUploadUrlResponse the upload URL for this request
	 * @param fileName the name of the file
	 * @param entity the http entity to upload
	 * @param sha1Checksum The SHA1 checksum of the content of the file. B2 will 
	 *     check this when the file is uploaded, to make sure that the file arrived 
	 *     correctly. It will be returned in the X-Bz-Content-Sha1 header when the 
	 *     file is downloaded.
	 * @param mimeType the mimeType (optional, will default to 'b2/x-auto' which
	 *     backblaze will attempt to determine automatically).  The MIME type of 
	 *     the content of the file, which will be returned in the Content-Type 
	 *     header when downloading the file. Use the Content-Type b2/x-auto 
	 *     to automatically set the stored Content-Type post upload. In the case 
	 *     where a file extension is absent or the lookup fails, the Content-Type 
	 *     is set to application/octet-stream.
	 * @param fileInfo A map of information that will be stored with the file. Up
	 *     to 10 of keys may be present.. The same file info headers sent with the 
	 *     upload will be returned with the download. 
	 *     
	 * @throws B2ApiException If the number of file info headers is greater than the allowable
	 */
	public B2UploadFileRequest(CloseableHttpClient client, 
			B2AuthorizeAccountResponse b2AuthorizeAccountResponse, 
			B2GetUploadUrlResponse b2GetUploadUrlResponse, 
			String fileName, 
			HttpEntity entity, 
			String sha1Checksum, 
			String mimeType, 
			Map<String, String> fileInfo) throws B2ApiException {

		super(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse.getUploadUrl());

		this.fileName = fileName;
		this.entity = entity;
		this.mimeType = mimeType;

		// now go through and add in the 'X-Bz-Info-*' headers
		if(null != fileInfo) {
			int fileInfoSize = fileInfo.size();
			if(fileInfoSize > MAX_FILE_INFO_HEADERS) {
				throw new B2ApiException(String.format("The maximum allowable file info size is '%d', you sent '%d'", MAX_FILE_INFO_HEADERS, fileInfoSize));
			}

			for(final String key : fileInfo.keySet()) {
				this.addHeader(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX + URLEncoder.encode(key), URLEncoder.encode(fileInfo.get(key)));
			}
		}

		this.addHeader(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1, sha1Checksum);
		// Override generic authorization header
		this.addHeader(HttpHeaders.AUTHORIZATION, b2GetUploadUrlResponse.getAuthorizationToken());
		if(null == mimeType) {
			this.addHeader(B2ResponseHeaders.HEADER_CONTENT_TYPE, CONTENT_TYPE_VALUE_B2_X_AUTO);
		} else {
			this.addHeader(B2ResponseHeaders.HEADER_CONTENT_TYPE, mimeType);
		}
		this.addHeader(B2ResponseHeaders.HEADER_X_BZ_FILE_NAME, URLEncoder.encode(fileName));
	}

	/**
	 * Return the file response 
	 * 
	 * @return the file response
	 * 
	 * @throws B2ApiException if something went wrong
	 */

	public B2FileResponse getResponse() throws B2ApiException {
		try {
			return new B2FileResponse(EntityUtils.toString(executePost(entity).getEntity()));
		} catch(IOException e) {
			throw new B2ApiException(e);
		}
	}
}
