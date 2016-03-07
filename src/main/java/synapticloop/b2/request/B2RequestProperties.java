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

public final class B2RequestProperties {
	public static final String KEY_ACCOUNT_ID = "accountId";
	public static final String KEY_BUCKET_ID = "bucketId";
	public static final String KEY_BUCKET_NAME = "bucketName";
	public static final String KEY_BUCKET_TYPE = "bucketType";
	public static final String KEY_FILE_ID = "fileId";
	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_MAX_FILE_COUNT = "maxFileCount";
	public static final String KEY_START_FILE_ID = "startFileId";
	public static final String KEY_START_FILE_NAME = "startFileName";
	public static final int MAX_FILE_COUNT_RETURN = 1000;
	public static final String REQUEST_PROPERTY_CONTENT_TYPE = "Content-Type";
	public static final String REQUEST_PROPERTY_AUTHORIZATION = "Authorization";

	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_X_BZ_CONTENT_SHA1 = "X-Bz-Content-Sha1";
	public static final String HEADER_X_BZ_FILE_NAME = "X-Bz-File-Name";
	public static final String HEADER_X_BZ_INFO_PREFIX = "x-bz-info-";

	public static final String KEY_MIME_TYPE = "mimeType";
	public static final String KEY_RANGE = "Range";

	public static final String VALUE_APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	public static final String VALUE_B2_X_AUTO = "b2/x-auto";
	public static final String VALUE_UTF_8 = "UTF-8";
}
