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

import org.apache.http.HttpHeaders;

public final class B2ResponseHeaders {
	public static final String HEADER_CONTENT_TYPE = HttpHeaders.CONTENT_TYPE;
	public static final String HEADER_CONTENT_LENGTH = HttpHeaders.CONTENT_LENGTH;

	public static final String HEADER_X_BZ_CONTENT_SHA1 = "X-Bz-Content-Sha1";
	public static final String HEADER_X_BZ_FILE_NAME = "X-Bz-File-Name";
	public static final String HEADER_X_BZ_FILE_ID = "X-Bz-File-Id";
	public static final String HEADER_X_BZ_INFO_PREFIX = "X-Bz-Info-";
	public static final String HEADER_X_BZ_PART_NUMBER = "X-Bz-Part-Number";
	public static final String HEADER_X_BZ_UPLOAD_TIMESTAMP = "X-Bz-Upload-Timestamp";
}
