package synapticloop.b2.response;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.input.NullInputStream;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.io.HttpMethodReleaseInputStream;

public class B2DownloadFileResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DownloadFileResponse.class);

	private static final Set<String> ignoredHeaders = new HashSet<String>();
	static {
		ignoredHeaders.add("x-xss-protection");
		ignoredHeaders.add("x-frame-options");

		ignoredHeaders.add(HttpHeaders.SERVER.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(HttpHeaders.ACCEPT_RANGES.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(HttpHeaders.CONTENT_RANGE.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(HttpHeaders.CACHE_CONTROL.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(HttpHeaders.DATE.toLowerCase(Locale.ENGLISH));

		// the following are mapped
		ignoredHeaders.add(HttpHeaders.CONTENT_LENGTH.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(HttpHeaders.CONTENT_TYPE.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(B2ResponseHeaders.HEADER_X_BZ_FILE_ID.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(B2ResponseHeaders.HEADER_X_BZ_FILE_NAME.toLowerCase(Locale.ENGLISH));
		ignoredHeaders.add(B2ResponseHeaders.HEADER_X_BZ_UPLOAD_TIMESTAMP.toLowerCase(Locale.ENGLISH));
	}

	private final InputStream stream;
	private final Long contentLength;
	private final String contentType;
	private final String fileId;
	private final String fileName;
	private final String contentSha1;
	private final String uploadTimestamp;

	private final Map<String, String> fileInfo = new HashMap<String, String>();

	/**
	 * Instantiate a bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param response The HTTP response object
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse(CloseableHttpResponse response) throws B2ApiException, IOException {
		if(null != response.getEntity()) {
			stream = new HttpMethodReleaseInputStream(response);
		} else {
			// HEAD responses do not have an entity
			stream = new NullInputStream(0L);
			EntityUtils.consume(response.getEntity());
		}

		contentLength = Long.parseLong(response.getFirstHeader(HttpHeaders.CONTENT_LENGTH).getValue());
		contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
		contentSha1 = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_CONTENT_SHA1).getValue();
		fileId = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_FILE_ID).getValue();
		fileName = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_FILE_NAME).getValue();
		uploadTimestamp = response.getFirstHeader(B2ResponseHeaders.HEADER_X_BZ_UPLOAD_TIMESTAMP).getValue();

		for (Header header : response.getAllHeaders()) {
			String headerName = header.getName();
			String headerValue = header.getValue();

			String headerNameLowerCase = headerName.toLowerCase(Locale.ENGLISH);

			if(headerNameLowerCase.startsWith(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX.toLowerCase(Locale.ENGLISH))) {
				fileInfo.put(headerName.substring(B2ResponseHeaders.HEADER_X_BZ_INFO_PREFIX.length()), headerValue);
			} else {
				if(!ignoredHeaders.contains(headerNameLowerCase)) {
					LOGGER.warn("Found a header named '{}' with value '{}', that was not mapped", headerName, headerValue);
				}
			}
		}
	}

	/**
	 * Get the content of the downloaded file, if this was a HEAD request, then 
	 * this will return null.
	 * 
	 * @return the downloaded file
	 */
	public InputStream getContent() { return this.stream; }

	/**
	 * Get the content length of the downloaded file
	 * 
	 * @return the length of the content
	 */
	public Long getContentLength() { return this.contentLength; }

	/**
	 * Get the content type of the downloaded file
	 * 
	 * @return the content type of the downloaded file
	 */
	public String getContentType() { return this.contentType; }

	/**
	 * Get the fileId that uniquely identifies this file
	 * 
	 * @return the fileId
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file as stored in the backblaze bucket
	 * 
	 * @return the name of the file as stored in the backblaze bucket
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the SHA1 of the returned content
	 * 
	 * @return the SHA1 of the returned content
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get the upload timestamp of the file
	 * 
	 * @return the upload timestamp of the file
	 */
	public String getUploadTimestamp() { return this.uploadTimestamp; }

	/**
	 * Get the file info for the file, this is stored as x-bz-info-* headers when 
	 * the file was uploaded.  This will be mapped with the x-bz-info- header
	 * prefix removed.  E.g. if the file was uploaded with the header: 
	 *   x-bz-info-tag=super-secret-tag
	 * This will be returned in the map as key(tag), value (super-secret-tag)
	 * 
	 * @return The map of the file info 
	 */
	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2DownloadFileResponse{");
		sb.append("content=").append(stream);
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}
