package synapticloop.b2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.request.B2AuthorizeAccountRequest;
import synapticloop.b2.request.B2CreateBucketRequest;
import synapticloop.b2.request.B2DeleteBucketRequest;
import synapticloop.b2.request.B2DeleteFileVersionRequest;
import synapticloop.b2.request.B2DownloadFileByIdRequest;
import synapticloop.b2.request.B2DownloadFileByNameRequest;
import synapticloop.b2.request.B2GetFileInfoRequest;
import synapticloop.b2.request.B2GetUploadUrlRequest;
import synapticloop.b2.request.B2HeadFileByIdRequest;
import synapticloop.b2.request.B2ListBucketsRequest;
import synapticloop.b2.request.B2ListFileNamesRequest;
import synapticloop.b2.request.B2ListFileVersionsRequest;
import synapticloop.b2.request.B2UpdateBucketRequest;
import synapticloop.b2.request.B2UploadFileRequest;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileInfoResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2ListFilesResponse;

/**
 * This is a wrapper class for the underlying calls to the request/response 
 * classes.
 * 
 * @author synapticloop
 */
public class B2ApiClient {

	private String accountId = null;
	private String applicationKey = null;

	B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;

	/**
	 * Instantiate a new B2ApiClient object
	 * 
	 * @param accountId The account id
	 * @param applicationKey the application key
	 */
	public B2ApiClient(String accountId, String applicationKey) {
		this.accountId = accountId;
		this.applicationKey = applicationKey;
	}

	/**
	 * Create a bucket with a specified name and bucket type
	 * 
	 * @param bucketName the name of the bucket
	 * @param bucketType the type of the bucket
	 * 
	 * @return the newly created bucket
	 * 
	 * @throws B2ApiException if the bucket could not be created, of there was an 
	 *     error with the authentication
	 */
	public B2BucketResponse createBucket(String bucketName, BucketType bucketType) throws B2ApiException {
		return(new B2CreateBucketRequest(getB2AuthorizeAccountResponse(), bucketName, bucketType).getResponse());
	}

	/**
	 * Delete a bucket by the bucket identifier
	 * 
	 * @param bucketId the id of the bucket to delete
	 * 
	 * @return the delete bucket response
	 * 
	 * @throws B2ApiException if something went wrong with the call, or the bucket was not empty
	 */
	public B2BucketResponse deleteBucket(String bucketId) throws B2ApiException {
		return(new B2DeleteBucketRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

	/**
	 * Delete a bucket including all of the files that reside within the bucket
	 * 
	 * @param bucketId the id of the bucker to delete
	 * 
	 * @return the deleted bucket response
	 * 
	 * @throws B2ApiException if there was an error deleting the bucket, or any 
	 *     of the enclosed files
	 */
	public B2BucketResponse deleteBucketFully(String bucketId) throws B2ApiException {
		B2ListFilesResponse b2ListFilesResponse = new B2ListFileVersionsRequest(getB2AuthorizeAccountResponse(), bucketId, 1000).getResponse();
		String nextFileName = b2ListFilesResponse.getNextFileName();
		String nextFileId = b2ListFilesResponse.getNextFileId();
		while(true) {
			List<B2FileInfoResponse> files = b2ListFilesResponse.getFiles();
			for (B2FileInfoResponse b2FileInfoResponse : files) {
				new B2DeleteFileVersionRequest(getB2AuthorizeAccountResponse(), b2FileInfoResponse.getFileName(), b2FileInfoResponse.getFileId()).getResponse();
			}

			if(null == nextFileName) {
				break;
			} else {
				b2ListFilesResponse = new B2ListFileVersionsRequest(getB2AuthorizeAccountResponse(), bucketId, nextFileName, nextFileId, 1000).getResponse();
				nextFileName = b2ListFilesResponse.getNextFileName();
				nextFileId = b2ListFilesResponse.getNextFileId();
			}
		}

		// now delete the bucket
		return(new B2DeleteBucketRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

	/**
	 * List all of the buckets in the account
	 * 
	 * @return the list of buckets for the account
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public List<B2BucketResponse> listBuckets() throws B2ApiException {
		return(new B2ListBucketsRequest(getB2AuthorizeAccountResponse()).getResponse());
	}

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
	 * @throws B2ApiException if something went wrong
	 */
	public B2FileResponse getFileInfo(String fileId) throws B2ApiException {
		return(new B2GetFileInfoRequest(getB2AuthorizeAccountResponse(), fileId).getResponse());
	}

	/**
	 * Upload a file to a bucket
	 * 
	 * @param bucketId the id of the bucket 
	 * @param fileName the name of the file that will be placed in the bucket 
	 *     (including any path separators '/')
	 * @param file the file to upload
	 * @param mimeType the mime type of the file, if null, then the mime type
	 *     will be attempted to be automatically mapped by the backblaze B2 API
	 *     see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *     for a list of content type mappings.
	 * @param fileInfo the file info map which will be set as 'X-Bz-Info-' headers
	 * 
	 * @return the uploaded file response
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 */
	public B2UploadFileRequest uploadFile(String bucketId, String fileName, File file, String mimeType, Map<String, String> fileInfo) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, fileName, file, mimeType, fileInfo));
	}

	/**
	 * Upload a file to a bucket
	 * 
	 * @param bucketId the id of the bucket 
	 * @param fileName the name of the file that will be placed in the bucket 
	 *     (including any path separators '/')
	 * @param file the file to upload
	 * @param fileInfo the file info map which will be set as 'X-Bz-Info-' headers
	 * 
	 * @return the uploaded file response
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 */

	public B2UploadFileRequest uploadFile(String bucketId, String fileName, File file, Map<String, String> fileInfo) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId, fileInfo).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, fileName, file));
	}

	/**
	 * Upload a file to a bucket
	 * 
	 * @param bucketId the id of the bucket 
	 * @param fileName the name of the file that will be placed in the bucket 
	 *     (including any path separators '/')
	 * @param file the file to upload
	 * @param mimeType the mime type of the file, if null, then the mime type
	 *     will be attempted to be automatically mapped by the backblaze B2 API
	 *     see <a href="https://www.backblaze.com/b2/docs/content-types.html">https://www.backblaze.com/b2/docs/content-types.html</a>
	 *     for a list of content type mappings.
	 * 
	 * @return the uploaded file response
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 */
	public B2UploadFileRequest uploadFile(String bucketId, String fileName, File file, String mimeType) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, fileName, file, mimeType));
	}

	/**
	 * Upload a file to a bucket, the mimetype will be automatically set by the 
	 * back-end B2 API system
	 * 
	 * @param bucketId the id of the bucket 
	 * @param fileName the name of the file that will be placed in the bucket 
	 *     (including any path separators '/')
	 * @param file the file to upload
	 * 
	 * @return the uploaded file response
	 * 
	 * @throws B2ApiException if there was an error uploading the file
	 */
	public B2UploadFileRequest uploadFile(String bucketId, String fileName, File file) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, fileName, file));
	}

	public B2DeleteFileVersionResponse deleteFileVersion(String fileName, String fileId) throws B2ApiException {
		return(new B2DeleteFileVersionRequest(getB2AuthorizeAccountResponse(), fileName, fileId).getResponse());
	}

	/**
	 * Update a busket to be a specified type
	 * 
	 * @param bucketId the id of the bucket to set
	 * @param bucketType the type of the bucket
	 * 
	 * @return the bucket response
	 * 
	 * @throws B2ApiException if there was an error updating the bucket
	 */
	public B2BucketResponse updateBucket(String bucketId, BucketType bucketType) throws B2ApiException {
		return(new B2UpdateBucketRequest(getB2AuthorizeAccountResponse(), bucketId, bucketType).getResponse());
	}

	/**
	 * Return a list of all of the files within a bucket with the specified ID,
	 * by default a maximum of 100 files are returned with this request.
	 * 
	 * @param bucketId the id of the bucket to list files
	 * 
	 * @return the list files response
	 * 
	 * @throws B2ApiException if there was an error with the call
	 */
	public B2ListFilesResponse listFileNames(String bucketId) throws B2ApiException {
		return(new B2ListFileNamesRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
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
	 */
	public B2ListFilesResponse listFileNames(String bucketId, String startFileName, Integer maxFileCount) throws B2ApiException {
		return(new B2ListFileNamesRequest(getB2AuthorizeAccountResponse(), bucketId, startFileName, maxFileCount).getResponse());
	}

	public B2ListFilesResponse listFileVersions(String bucketId) throws B2ApiException {
		return(new B2ListFileVersionsRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

	public B2ListFilesResponse listFileVersions(String bucketId, String startFileName) throws B2ApiException {
		return(new B2ListFileVersionsRequest(getB2AuthorizeAccountResponse(), bucketId, startFileName, null, null).getResponse());
	}

	public B2ListFilesResponse listFileVersions(String bucketId, String startFileName, String startFileId, Integer maxFileCount) throws B2ApiException {
		return(new B2ListFileVersionsRequest(getB2AuthorizeAccountResponse(), bucketId, startFileName, startFileId, maxFileCount).getResponse());
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
	 */
	public void downloadFileByNameToFile(String bucketName, String fileName, File file) throws B2ApiException {
		try {
			FileUtils.copyInputStreamToFile(new B2DownloadFileByNameRequest(getB2AuthorizeAccountResponse(), bucketName, fileName).getResponse().getContent(), file);
		} catch (IOException ex) {
			throw new B2ApiException("Could not download to file", ex);
		}
	}

	/**
	 * Download a named file from a named bucket to an byte[].  This is a 
	 * utility method which will automatically convert the response stream to a
	 * byte[].
	 * 
	 * Note: This will not return any of the headers that accompanied the download.  
	 * See downloadFileByName to retrieve the complete response including sha1, 
	 * content length, content type and all headers.
	 * 
	 * @param bucketName The name of the bucket to download the file from
	 * @param fileName the name of the file to download
	 * 
	 * @return the array of bytes from the download
	 * 
	 * @throws B2ApiException if there was an error with the call
	 */
	public byte[] downloadFileByNameToBytes(String bucketName, String fileName) throws B2ApiException {
		try {
			return(IOUtils.toByteArray(new B2DownloadFileByNameRequest(getB2AuthorizeAccountResponse(), bucketName, fileName).getResponse().getContent()));
		} catch (IOException ex) {
			throw new B2ApiException("Could not download to bytes", ex);
		}
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
	 */
	public InputStream downloadFileByNameToStream(String bucketName, String fileName) throws B2ApiException {
		return(new B2DownloadFileByNameRequest(getB2AuthorizeAccountResponse(), bucketName, fileName).getResponse().getContent());
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
	 */
	public B2DownloadFileResponse downloadFileByName(String bucketName, String fileName) throws B2ApiException {
		return(new B2DownloadFileByNameRequest(getB2AuthorizeAccountResponse(), bucketName, fileName).getResponse());
	}

	public void downloadFileByIdToFile(String bucketId, String FileId, File file) {
	}

	public byte[] downloadByFileIdToBytes(String bucketId, String fileId) {
		return(new byte[] {});
	}

	public InputStream downloadFileByIdToStream(String fileId) throws B2ApiException {
		return(new B2DownloadFileByIdRequest(getB2AuthorizeAccountResponse(), fileId).getResponse().getContent());
	}

	public B2DownloadFileResponse downloadFileById(String fileId) throws B2ApiException {
		return(new B2DownloadFileByIdRequest(getB2AuthorizeAccountResponse(), fileId).getResponse());
	}

	public B2DownloadFileResponse headFileById(String fileId) throws B2ApiException {
		return(new B2HeadFileByIdRequest(getB2AuthorizeAccountResponse(), fileId).getResponse());
	}

	private synchronized B2AuthorizeAccountResponse getB2AuthorizeAccountResponse() throws B2ApiException {
		if(null == b2AuthorizeAccountResponse) {
			b2AuthorizeAccountResponse = new B2AuthorizeAccountRequest(accountId, applicationKey).getResponse();
		}
		return(b2AuthorizeAccountResponse);
	}
}
