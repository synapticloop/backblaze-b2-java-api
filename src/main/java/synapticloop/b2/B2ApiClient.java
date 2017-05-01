package synapticloop.b2;

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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.request.B2AuthorizeAccountRequest;
import synapticloop.b2.request.B2CancelLargeFileRequest;
import synapticloop.b2.request.B2CreateBucketRequest;
import synapticloop.b2.request.B2DeleteBucketRequest;
import synapticloop.b2.request.B2DeleteFileVersionRequest;
import synapticloop.b2.request.B2DownloadFileByIdRequest;
import synapticloop.b2.request.B2DownloadFileByNameRequest;
import synapticloop.b2.request.B2FinishLargeFileRequest;
import synapticloop.b2.request.B2GetDownloadAuthorizationRequest;
import synapticloop.b2.request.B2GetFileInfoRequest;
import synapticloop.b2.request.B2GetUploadPartUrlRequest;
import synapticloop.b2.request.B2GetUploadUrlRequest;
import synapticloop.b2.request.B2HeadFileByIdRequest;
import synapticloop.b2.request.B2HideFileRequest;
import synapticloop.b2.request.B2ListBucketsRequest;
import synapticloop.b2.request.B2ListFileNamesRequest;
import synapticloop.b2.request.B2ListFileVersionsRequest;
import synapticloop.b2.request.B2ListPartsRequest;
import synapticloop.b2.request.B2ListUnfinishedLargeFilesRequest;
import synapticloop.b2.request.B2StartLargeFileRequest;
import synapticloop.b2.request.B2UpdateBucketRequest;
import synapticloop.b2.request.B2UploadFileRequest;
import synapticloop.b2.request.B2UploadPartRequest;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2FinishLargeFileResponse;
import synapticloop.b2.response.B2GetUploadPartUrlResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2HideFileResponse;
import synapticloop.b2.response.B2ListFilesResponse;
import synapticloop.b2.response.B2ListPartsResponse;
import synapticloop.b2.response.B2StartLargeFileResponse;
import synapticloop.b2.response.B2UploadPartResponse;
import synapticloop.b2.util.ChecksumHelper;

/**
 * This is a wrapper class for the underlying calls to the request/response
 * classes.
 *
 * @author synapticloop
 */
public class B2ApiClient {

	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse;

	private final CloseableHttpClient client;

	/**
	 * Create a B2ApiClient and authenticate
	 *
	 * @param accountId The account id
	 * @param applicationKey the application key
	 *
	 * @throws B2ApiException if there was an error authenticating the account
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ApiClient(String accountId, String applicationKey) throws B2ApiException, IOException {
		this();
		this.b2AuthorizeAccountResponse = authenticate(accountId, applicationKey);
	}

	/**
	 * Must authenticate first before API actions are available. Using default HTTP client configuration
	 *
	 * @see #authenticate(String, String)
	 */
	public B2ApiClient() {
		this(HttpClients.createDefault());
	}

	/**
	 * Must authenticate first before API actions are available
	 *
	 * @param client Shared HTTP client
	 *
	 * @see #authenticate(String, String)
	 */
	public B2ApiClient(CloseableHttpClient client) {
		this.client = client;
	}

	/**
	 * return the authorize account response.  This is only done once and is
	 * cached for further use.
	 *
	 * @param accountId The account id
	 * @param applicationKey the application key
	 *
	 * @return the authorize account response
	 *
	 * @throws B2ApiException if there was an error authenticating
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2AuthorizeAccountResponse authenticate(String accountId, String applicationKey) throws B2ApiException, IOException {
		return b2AuthorizeAccountResponse = new B2AuthorizeAccountRequest(client, accountId, applicationKey).getResponse();
	}

	/**
	 * Get the download URL for the authorized response
	 *
	 * @return the download URL for the authorized response
	 */
	public String getDownloadUrl() {
		return b2AuthorizeAccountResponse.getDownloadUrl();
	}

	/**
	 * Get the API url
	 *
	 * @return the API URL for backblaze
	 */
	public String getApiUrl() {
		return b2AuthorizeAccountResponse.getApiUrl();
	}

	/**
	 * Release all resources from the connection pool.
	 *
	 * @throws IOException if the client could not be closed
	 */
	public void close() throws IOException {
		client.close();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   BUCKET RELATED API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Create a bucket with the specified name and bucket type (either private
	 * or public)
	 *
	 * @param bucketName the name of the bucket
	 * @param bucketType the type of the bucket
	 *
	 * @return the newly created bucket
	 *
	 * @throws B2ApiException if the bucket could not be created, of there was an
	 *     error with the authentication
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2BucketResponse createBucket(String bucketName, BucketType bucketType) throws B2ApiException, IOException {
		return new B2CreateBucketRequest(client, b2AuthorizeAccountResponse, bucketName, bucketType).getResponse();
	}

	/**
	 * Delete a bucket by the bucket identifier
	 *
	 * @param bucketId the id of the bucket to delete
	 *
	 * @return the delete bucket response
	 *
	 * @throws B2ApiException if something went wrong with the call, or the bucket was not empty
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2BucketResponse deleteBucket(String bucketId) throws B2ApiException, IOException {
		return new B2DeleteBucketRequest(client, b2AuthorizeAccountResponse, bucketId).getResponse();
	}

	/**
	 * Update a bucket to be a specified type
	 *
	 * @param bucketId the id of the bucket to set
	 * @param bucketType the type of the bucket
	 *
	 * @return the bucket response
	 *
	 * @throws B2ApiException if there was an error updating the bucket
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2BucketResponse updateBucket(String bucketId, BucketType bucketType, LifecycleRule... lifecycleRules) throws B2ApiException, IOException {
		return new B2UpdateBucketRequest(client, b2AuthorizeAccountResponse, bucketId, bucketType, lifecycleRules).getResponse();
	}

	/**
	 * List all of the buckets in the account
	 *
	 * @return the list of buckets for the account
	 *
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public List<B2BucketResponse> listBuckets() throws B2ApiException, IOException {
		return new B2ListBucketsRequest(client, b2AuthorizeAccountResponse).getResponse().getBuckets();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   FILE INFORMATION API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Retrieve the file information for a particular fileId, this includes all
	 * of the 'X-Bz-Info-*' headers that were passed in with the file creation
	 * in a map (without the 'X-Bz-Info-' prefix).  For example, if you uploaded
	 * a file with a header X-Bz-Info-Tag, the returned file info map would
	 * contain a key of 'Tag'
	 *
	 * @param fileId the file ID to retrieve the information on
	 *
	 * @return the File Response
	 *
	 * @throws B2ApiException if something went wrong
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2FileResponse getFileInfo(String fileId) throws B2ApiException, IOException {
		return new B2GetFileInfoRequest(client, b2AuthorizeAccountResponse, fileId).getResponse();
	}

	/**
	 * Perform a HEAD request on a file which will return the information
	 * associated with it.
	 *
	 * @param fileId the id of the file to retrieve the information for
	 *
	 * @return the download file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */

	public B2DownloadFileResponse headFileById(String fileId) throws B2ApiException, IOException {
		return new B2HeadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId).getResponse();
	}


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   FILE UPLOAD API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * An uploadUrl and upload authorizationToken are valid for 24 hours or until the endpoint rejects an upload, see b2_upload_file.
	 * You can upload as many files to this URL as you need. To achieve faster upload speeds, request multiple uploadUrls and
	 * upload your files to these different endpoints in parallel.
	 *
	 * @param bucketId the id of the bucket to upload to
	 * @return Upload URL. Not be shared between threads but can be used for multiple parts
	 * @throws B2ApiException if there was an error with the request
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2GetUploadUrlResponse getUploadUrl(String bucketId) throws B2ApiException, IOException {
		return new B2GetUploadUrlRequest(client, b2AuthorizeAccountResponse, bucketId).getResponse();
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param bucketId     the id of the bucket
	 * @param fileName     the name of the file that will be placed in the bucket
	 *                     (including any path separators '/')
	 * @param entity       the file content to upload
	 * @param sha1Checksum the checksum for the file
	 * @param mimeType     the mime type of the file, if null, then the mime type
	 *                     will be attempted to be automatically mapped by the backblaze B2 API
	 *                     see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                     for a list of content type mappings.
	 * @param fileInfo     the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(String bucketId, String fileName, HttpEntity entity, String sha1Checksum, String mimeType, Map<String, String> fileInfo) throws B2ApiException, IOException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = this.getUploadUrl(bucketId);
		return this.uploadFile(b2GetUploadUrlResponse, fileName, entity, sha1Checksum, mimeType, fileInfo);
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param fileName               the name of the file that will be placed in the bucket
	 *                               (including any path separators '/')
	 * @param entity                 the file content to upload
	 * @param sha1Checksum           the checksum for the file
	 * @param mimeType               the mime type of the file, if null, then the mime type
	 *                               will be attempted to be automatically mapped by the backblaze B2 API
	 *                               see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                               for a list of content type mappings.
	 * @param fileInfo               the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, HttpEntity entity, String sha1Checksum, String mimeType, Map<String, String> fileInfo) throws B2ApiException, IOException {
		return new B2UploadFileRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, entity, sha1Checksum, mimeType, fileInfo).getResponse();
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName the name of the file that will be placed in the bucket
	 *                 (including any path separators '/')
	 * @param file     the file to upload
	 * @param mimeType the mime type of the file, if null, then the mime type
	 *                 will be attempted to be automatically mapped by the backblaze B2 API
	 *                 see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                 for a list of content type mappings.
	 * @param fileInfo the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(String bucketId, String fileName, File file, String mimeType, Map<String, String> fileInfo) throws B2ApiException, IOException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = this.getUploadUrl(bucketId);
		return this.uploadFile(b2GetUploadUrlResponse, fileName, file, mimeType, fileInfo);
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param fileName               the name of the file that will be placed in the bucket
	 *                               (including any path separators '/')
	 * @param file                   the file to upload
	 * @param mimeType               the mime type of the file, if null, then the mime type
	 *                               will be attempted to be automatically mapped by the backblaze B2 API
	 *                               see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                               for a list of content type mappings.
	 * @param fileInfo               the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType, Map<String, String> fileInfo) throws B2ApiException, IOException {
		return new B2UploadFileRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file,
				ChecksumHelper.calculateSha1(file), mimeType, fileInfo).getResponse();
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName the name of the file that will be placed in the bucket
	 *                 (including any path separators '/')
	 * @param file     the file to upload
	 * @param fileInfo the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(String bucketId, String fileName, File file, Map<String, String> fileInfo) throws B2ApiException, IOException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = this.getUploadUrl(bucketId);
		return this.uploadFile(b2GetUploadUrlResponse, fileName, file, fileInfo);
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param fileName               the name of the file that will be placed in the bucket
	 *                               (including any path separators '/')
	 * @param file                   the file to upload
	 * @param fileInfo               the file info map which will be set as 'X-Bz-Info-' headers
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, Map<String, String> fileInfo) throws B2ApiException, IOException {
		return new B2UploadFileRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file,
				ChecksumHelper.calculateSha1(file), fileInfo).getResponse();
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName the name of the file that will be placed in the bucket
	 *                 (including any path separators '/')
	 * @param file     the file to upload
	 * @param mimeType the mime type of the file, if null, then the mime type
	 *                 will be attempted to be automatically mapped by the backblaze B2 API
	 *                 see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                 for a list of content type mappings.
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(String bucketId, String fileName, File file, String mimeType) throws B2ApiException, IOException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = this.getUploadUrl(bucketId);
		return this.uploadFile(b2GetUploadUrlResponse, fileName, file, mimeType);
	}

	/**
	 * Upload a file to a bucket
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param fileName               the name of the file that will be placed in the bucket
	 *                               (including any path separators '/')
	 * @param file                   the file to upload
	 * @param mimeType               the mime type of the file, if null, then the mime type
	 *                               will be attempted to be automatically mapped by the backblaze B2 API
	 *                               see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                               for a list of content type mappings.
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file, String mimeType) throws B2ApiException, IOException {
		return new B2UploadFileRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName, file,
				ChecksumHelper.calculateSha1(file), mimeType).getResponse();
	}

	/**
	 * Upload a file to a bucket, the mimetype will be automatically set by the
	 * back-end B2 API system
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName the name of the file that will be placed in the bucket
	 *                 (including any path separators '/')
	 * @param file     the file to upload
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(String bucketId, String fileName, File file) throws B2ApiException, IOException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = this.getUploadUrl(bucketId);
		return this.uploadFile(b2GetUploadUrlResponse, fileName, file);
	}

	/**
	 * Upload a file to a bucket, the mimetype will be automatically set by the
	 * back-end B2 API system
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param fileName               the name of the file that will be placed in the bucket
	 *                               (including any path separators '/')
	 * @param file                   the file to upload
	 * @return the uploaded file response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FileResponse uploadFile(B2GetUploadUrlResponse b2GetUploadUrlResponse, String fileName, File file) throws B2ApiException, IOException {
		return new B2UploadFileRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, fileName,
				file, ChecksumHelper.calculateSha1(file)).getResponse();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   LARGE FILE UPLOAD API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * When you upload part of a large file to B2, you must call b2_get_upload_part_url first to get the URL for uploading.
	 * Then, you use b2_upload_part on this URL to upload your data.
	 *
	 * @param fileId the id of the file to upload
	 * @return Upload URL. Not be shared between threads but can be used for multiple parts
	 * @throws B2ApiException if there was an error with the request
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2GetUploadPartUrlResponse getUploadPartUrl(String fileId) throws B2ApiException, IOException {
		return new B2GetUploadPartUrlRequest(client, b2AuthorizeAccountResponse, fileId).getResponse();
	}

	/**
	 * Start large file upload
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName the name of the file that will be placed in the bucket
	 * @param mimeType the mime type of the file, if null, then the mime type
	 *                 will be attempted to be automatically mapped by the backblaze B2 API
	 *                 see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *                 for a list of content type mappings.
	 * @param fileInfo the file info map which will be set as 'X-Bz-Info-' headers
	 * 
	 * @return The unique identifier for the file
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException if there was an error with the underlying transport
	 */
	public B2StartLargeFileResponse startLargeFileUpload(String bucketId, String fileName, String mimeType, Map<String, String> fileInfo) throws B2ApiException, IOException {
		return new B2StartLargeFileRequest(client, b2AuthorizeAccountResponse, bucketId, fileName, mimeType, fileInfo).getResponse();
	}

	/**
	 * Cancel large file upload
	 *
	 * @param fileId The ID returned by b2_start_large_file.
	 * 
	 * @return File response
	 * 
	 * @throws B2ApiException if there was an error canceling the upload
	 * @throws IOException if there was an error with the underlying transport
	 */
	public B2FileResponse cancelLargeFileUpload(String fileId) throws B2ApiException, IOException {
		return new B2CancelLargeFileRequest(client, b2AuthorizeAccountResponse, fileId).getResponse();
	}

	/**
	 * Finish large file upload. Converts the parts that have been uploaded into a single B2 file.
	 *
	 * @param fileId The ID returned by b2_start_large_file.
	 * @param partSha1Array the array of sha1 sums for each of the parts in order
	 * 
	 * @return File response
	 * 
	 * @throws B2ApiException if there was an error finishing the upload
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2FinishLargeFileResponse finishLargeFileUpload(String fileId, String[] partSha1Array) throws B2ApiException, IOException {
		return new B2FinishLargeFileRequest(client, b2AuthorizeAccountResponse, fileId, partSha1Array).getResponse();
	}

	/**
	 * Upload large file upload part
	 *
	 * @param fileId       the id of the file to upload
	 * @param partNumber   A number from 1 to 10000. The parts uploaded for one file must have contiguous numbers, starting with 1.
	 * @param entity       Part content body
	 * @param sha1Checksum the checksum for the part
	 * 
	 * @return Upload response
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2UploadPartResponse uploadLargeFilePart(String fileId, int partNumber, HttpEntity entity, String sha1Checksum) throws B2ApiException, IOException {
		final B2GetUploadPartUrlResponse b2GetUploadUrlResponse = this.getUploadPartUrl(fileId);
		return this.uploadLargeFilePart(b2GetUploadUrlResponse, partNumber, entity, sha1Checksum);
	}

	/**
	 * Upload large file upload part
	 *
	 * @param b2GetUploadUrlResponse Upload URL.
	 * @param partNumber             A number from 1 to 10000. The parts uploaded for one file must have contiguous numbers, starting with 1.
	 * @param entity                 Part content body
	 * @param sha1Checksum           the checksum for the part
	 * @return Upload response
	 * @throws B2ApiException if there was an error uploading the file
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2UploadPartResponse uploadLargeFilePart(B2GetUploadPartUrlResponse b2GetUploadUrlResponse, int partNumber, HttpEntity entity, String sha1Checksum) throws B2ApiException, IOException {
		return new B2UploadPartRequest(client, b2AuthorizeAccountResponse, b2GetUploadUrlResponse, partNumber, entity, sha1Checksum).getResponse();
	}

	/**
	 * Lists information about large file uploads that have been started, but have not been finished or cancelled.
	 * Uploads are listed in the order they were started, with the oldest one first
	 *
	 * @param bucketId the id of the bucket
	 * @param startFileId the start fileId to list from
	 * @param maxFileCount the maximum number of files to return
	 * 
	 * @return An array of objects, each one describing one unfinished file
	 * 
	 * @throws B2ApiException if there was an error listing the files
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listUnfinishedLargeFiles(String bucketId, String startFileId, Integer maxFileCount) throws B2ApiException, IOException {
		return new B2ListUnfinishedLargeFilesRequest(client, b2AuthorizeAccountResponse, bucketId, startFileId, maxFileCount).getResponse();
	}

	/**
	 * @param fileId          The ID returned by b2_start_large_file. This is the file whose parts will be listed.
	 * @param startPartNumber Null to start
	 * @param maxPartCount    The maximum number of parts to return
	 * 
	 * @return This call returns at most 1000 entries, but it can be called repeatedly to scan through all of the parts for an upload.
	 * 
	 * @throws B2ApiException if there was an error listing the parts
	 * @throws IOException    if there was an error communicating with the API service
	 */
	public B2ListPartsResponse listParts(String fileId, Integer startPartNumber, Integer maxPartCount) throws B2ApiException, IOException {
		return new B2ListPartsRequest(client, b2AuthorizeAccountResponse, fileId, startPartNumber, maxPartCount).getResponse();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   DELETE FILE API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Delete a version of a file
	 *
	 * @param fileName The name of the file to delete
	 * @param fileId The version of the file to delete
	 *
	 * @return the deleted file response
	 *
	 * @throws B2ApiException if there was an error deleting the file
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DeleteFileVersionResponse deleteFileVersion(String fileName, String fileId) throws B2ApiException, IOException {
		return new B2DeleteFileVersionRequest(client, b2AuthorizeAccountResponse, fileName, fileId).getResponse();
	}

	/**
	 * Hides a file so that downloading by name will not find the file, but previous versions of the file are still stored.
	 *
	 * @param bucketId the id of the bucket
	 * @param fileName The name of the file to hide
	 *
	 * @return The hide response
	 *
	 * @throws B2ApiException if there was an error hiding the file
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2HideFileResponse hideFile(String bucketId, String fileName) throws B2ApiException, IOException {
		return new B2HideFileRequest(client, b2AuthorizeAccountResponse, bucketId, fileName).getResponse();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   FILE LISTING API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Return a list of all of the files within a bucket with the specified ID,
	 * by default a maximum of 100 files are returned with this request.
	 *
	 * @param bucketId the id of the bucket to list files
	 *
	 * @return the list files response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileNames(String bucketId) throws B2ApiException, IOException {
		return new B2ListFileNamesRequest(client, b2AuthorizeAccountResponse, bucketId).getResponse();
	}

	/**
	 * Return a list of all of the files within a bucket with the specified ID,
	 * by default a maximum of 100 files are returned with this request.
	 *
	 * @param bucketId the id of the bucket to list
	 * @param startFileName the start file name, or if null, this will be the first file
	 * @param maxFileCount (optional) if null, the default is 100, the maximum number
	 *     to be returned is 1000
	 *
	 * @return the list of files response
	 *
	 * @throws B2ApiException if there was an error with the call,
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileNames(String bucketId, String startFileName, Integer maxFileCount) throws B2ApiException, IOException {
		return new B2ListFileNamesRequest(client, b2AuthorizeAccountResponse, bucketId, startFileName, maxFileCount, null, null).getResponse();
	}

	/**
	 * Return a list of all of the files within a bucket with the specified ID,
	 * by default a maximum of 100 files are returned with this request.
	 *
	 * @param bucketId the id of the bucket to list
	 * @param startFileName the start file name, or if null, this will be the first file
	 * @param maxFileCount (optional) if null, the default is 100, the maximum number to be returned is 1000
	 * @param prefix Files returned will be limited to those with the given prefix. Defaults to the empty string, which matches all files.
	 * @param delimiter Files returned will be limited to those within the top folder, or any one subfolder. Defaults to NULL. Folder names will also be returned. The delimiter character will be used to "break" file names into folders.
	 * @return the list of files response
	 *
	 * @throws B2ApiException if there was an error with the call,
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileNames(String bucketId, String startFileName, Integer maxFileCount, String prefix, String delimiter) throws B2ApiException, IOException {
		return new B2ListFileNamesRequest(client, b2AuthorizeAccountResponse, bucketId, startFileName, maxFileCount, prefix, delimiter).getResponse();
	}

	/**
	 * List the files and versions for a specific bucket
	 *
	 * @param bucketId the id of the bucket to list
	 *
	 * @return the list file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileVersions(String bucketId) throws B2ApiException, IOException {
		return new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, 1000).getResponse();
	}

	/**
	 * List the file versions in a bucket starting at a specific file name
	 *
	 * @param bucketId the id of the bucket
	 * @param startFileName the file name to start with
	 *
	 * @return the list file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileVersions(String bucketId, String startFileName) throws B2ApiException, IOException {
		return new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, null, startFileName, null, null, null).getResponse();
	}

	/**
	 * List the file versions in a bucket starting at a specific file name and file id
	 *
	 * @param bucketId the id of the bucket
	 * @param startFileName the file name to start with
	 * @param startFileId the id of the file to start with
	 * @param maxFileCount the maximum number of files to return (must be less
	 *     than or equal to 1000, else an error is thrown)
	 *
	 * @return the list files response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileVersions(String bucketId, String startFileName, String startFileId, Integer maxFileCount) throws B2ApiException, IOException {
		return new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, maxFileCount, startFileName, startFileId, null, null).getResponse();
	}

	/**
	 * List the file versions in a bucket starting at a specific file name and file id
	 *
	 * @param bucketId the id of the bucket
	 * @param startFileName the file name to start with
	 * @param startFileId the id of the file to start with
	 * @param maxFileCount the maximum number of files to return (must be less than or equal to 1000, else an error is thrown)
	 * @param prefix Files returned will be limited to those with the given prefix. Defaults to the empty string, which matches all files.
	 * @param delimiter Files returned will be limited to those within the top folder, or any one subfolder. Defaults to NULL. Folder names will also be returned. The delimiter character will be used to "break" file names into folders.
	 *
	 * @return the list files response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2ListFilesResponse listFileVersions(String bucketId, String startFileName, String startFileId, Integer maxFileCount, String prefix, String delimiter) throws B2ApiException, IOException {
		return new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, maxFileCount, startFileName, startFileId, prefix, delimiter).getResponse();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *
	 *   DOWNLOAD FILE API ACTIONS
	 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Used to generate an authorization token that can be used to download files with the specified prefix
	 * from a private B2 bucket. Returns an authorization token that can be passed to b2_download_file_by_name
	 * in the Authorization header or as an Authorization parameter.
	 *
	 * @param bucketId the id of the bucket
	 * @param fileNamePrefix The file name prefix of files the download authorization token will allow b2_download_file_by_name to access.
	 * @param validDurationInSeconds The number of seconds before the authorization token will expire.
	 *                                  The maximum value is 604800 which is one week in seconds
	 * @return The authorization token that can be passed in the Authorization header or as an Authorization
	 * parameter to b2_download_file_by_name to access files beginning with the file name prefix.
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public String getDownloadAuthorization(String bucketId, String fileNamePrefix, Integer validDurationInSeconds) throws B2ApiException, IOException {
		return new B2GetDownloadAuthorizationRequest(client, b2AuthorizeAccountResponse, bucketId, fileNamePrefix, validDurationInSeconds).getResponse().getAuthorizationToken();
	}

	/**
	 * Download a named file from a named bucket to an output file.  This is a
	 * utility method which will automatically write the content to the file.
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 * @param file the file to write out the data to
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public void downloadFileByNameToFile(String bucketName, String fileName, File file) throws B2ApiException, IOException {
		FileUtils.copyInputStreamToFile(new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName)
				.getResponse().getContent(), file);
	}

	/**
	 * Download a range of bytes from a named file from a named bucket to an
	 * output file.  This is a utility method which will automatically write
	 * the content to the file.  This is a utility method which will
	 * automatically return the response stream.  The range starts at 0
	 * (zero) and goes up to the end.  Both are inclusive - e.g. a range of
	 * 0-5 will return 6 (six) bytes.   If the range values are not correct,
	 * the complete file will be downloaded
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 * @param file the file to write out the data to
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public void downloadFileRangeByNameToFile(String bucketName, String fileName, File file, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		FileUtils.copyInputStreamToFile(new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName)
				.getResponse().getContent(), file);
	}

	/**
	 * Download a named file from a named bucket and return the input stream from
	 * the HTTP response.  This is a utility method which will automatically return
	 * the response stream.
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 *
	 * @return the input stream
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public InputStream downloadFileByNameToStream(String bucketName, String fileName) throws B2ApiException, IOException {
		return new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName).getResponse().getContent();
	}

	/**
	 * Download a range of bytes from a named file from a named bucket to an
	 * input stream from the HTTP response.  This is a utility method which
	 * will automatically return the response stream.  The range starts at 0
	 * (zero) and goes up to the end.  Both are inclusive - e.g. a range of
	 * 0-5 will return 6 (six) bytes.   If the range values are not correct,
	 * the complete file will be downloaded
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @return the input stream
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public InputStream downloadFileRangeByNameToStream(String bucketName, String fileName, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		return new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName).getResponse().getContent();
	}

	/**
	 * Download a named file from a named bucket and return the download file
	 * response, which includes the headers, the file info and the response
	 * stream.
	 *
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 *
	 * @return the download file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse downloadFileByName(String bucketName, String fileName) throws B2ApiException, IOException {
		return new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName).getResponse();
	}

	/**
	 * Download a range of bytes from named file from a named bucket and return
	 * the download file response, which includes the headers, the file info and
	 * the response stream.  The range starts at 0 (zero) and  goes up to the
	 * end.  Both are inclusive - e.g. a range of 0-5 will return 6 (six) bytes.
	 * If the range values are not correct, the complete file will be downloaded
	 *
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @return the download file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse downloadFileRangeByName(String bucketName, String fileName, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		return new B2DownloadFileByNameRequest(client, b2AuthorizeAccountResponse, bucketName, fileName, rangeStart, rangeEnd).getResponse();
	}

	/**
	 * Download a file
	 *
	 * @param fileId the id of the file to download
	 *
	 * @return the download file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse downloadFileById(String fileId) throws B2ApiException, IOException {
		return new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId).getResponse();
	}

	/**
	 * Download a partial range of file data, the range starts at 0 (zero) and
	 * goes up to the end.  Both are inclusive - e.g. a range of 0-5 will return
	 * 6 (six) bytes.  If the range values are not correct, the
	 * complete file will be downloaded
	 *
	 * @param fileId the id of the file to download
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @return the download file response
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public B2DownloadFileResponse downloadFileRangeById(String fileId, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		return new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId, rangeStart, rangeEnd).getResponse();
	}

	/**
	 * Download the file to a local file
	 *
	 * @param fileId The ID of the file to download
	 * @param file The file to download to
	 *
	 * @throws B2ApiException if there was an error downloading the file
	 * @throws IOException if there was an error communicating with the API service
	 */
	public void downloadFileByIdToFile(String fileId, File file) throws B2ApiException, IOException {
		FileUtils.copyInputStreamToFile(new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId).getResponse().getContent(), file);
	}

	/**
	 * Download a partial range of file data to a local file, the range starts
	 * at 0 (zero) and goes up to the end.  Both are inclusive - e.g. a range of
	 * 0-5 will return 6 (six) bytes.  If the range values are not correct, the
	 * complete file will be downloaded
	 *
	 * @param fileId The ID of the file to download
	 * @param file The file to download to
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @throws B2ApiException if there was an error downloading the file
	 * @throws IOException if there was an error communicating with the API service
	 */
	public void downloadFileRangeByIdToFile(String fileId, File file, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		FileUtils.copyInputStreamToFile(new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId, rangeStart, rangeEnd)
				.getResponse().getContent(), file);
	}

	/**
	 * Download a file to an input stream
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param fileId the id of the file to download
	 *
	 * @return the input stream for the file
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public InputStream downloadFileByIdToStream(String fileId) throws B2ApiException, IOException {
		return new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId).getResponse().getContent();
	}

	/**
	 * Download a partial range of file data to an input stream, the range starts
	 * at 0 (zero) and goes up to the end.  Both are inclusive - e.g. a range of
	 * 0-5 will return 6 (six) bytes.  If the range values are not correct, the
	 * complete file will be downloaded
	 *
	 * Note: This will not return any of the headers that accompanied the download.
	 * See downloadFileByName to retrieve the complete response including sha1,
	 * content length, content type and all headers.
	 *
	 * @param fileId the id of the file to download
	 * @param rangeStart the start range (byte) offset for the content (inclusive)
	 * @param rangeEnd the end range (byte) offset for the content (inclusive)
	 *
	 * @return the input stream for the file
	 *
	 * @throws B2ApiException if there was an error with the call
	 * @throws IOException if there was an error communicating with the API service
	 */
	public InputStream downloadFileRangeByIdToStream(String fileId, long rangeStart, long rangeEnd) throws B2ApiException, IOException {
		return new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, fileId, rangeStart, rangeEnd)
				.getResponse().getContent();
	}
}
