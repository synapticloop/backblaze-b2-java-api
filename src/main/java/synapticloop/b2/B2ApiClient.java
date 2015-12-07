package synapticloop.b2;

import java.io.File;
import java.util.List;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.request.B2AuthorizeAccountRequest;
import synapticloop.b2.request.B2CreateBucketRequest;
import synapticloop.b2.request.B2DeleteBucketRequest;
import synapticloop.b2.request.B2DeleteFileVersionRequest;
import synapticloop.b2.request.B2GetFileInfoRequest;
import synapticloop.b2.request.B2GetUploadUrlRequest;
import synapticloop.b2.request.B2ListBucketsRequest;
import synapticloop.b2.request.B2ListFileNamesRequest;
import synapticloop.b2.request.B2ListFileVersionsRequest;
import synapticloop.b2.request.B2UpdateBucketRequest;
import synapticloop.b2.request.B2UploadFileRequest;
import synapticloop.b2.request.BucketType;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2ListFilesResponse;

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
	 * @throws B2ApiException if something went wrong
	 */
	public B2BucketResponse createBucket(String bucketName, BucketType bucketType) throws B2ApiException {
		return(new B2CreateBucketRequest(getB2AuthorizeAccountResponse(), bucketName, bucketType).getResponse());
	}

	public B2BucketResponse deleteBucket(String bucketId) throws B2ApiException {
		return(new B2DeleteBucketRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

	public List<B2BucketResponse> listBuckets() throws B2ApiException {
		return(new B2ListBucketsRequest(getB2AuthorizeAccountResponse()).getResponse());
	}

	public B2FileResponse getFileInfo(String fileId) throws B2ApiException {
		return(new B2GetFileInfoRequest(getB2AuthorizeAccountResponse(), fileId).getResponse());
	}

	public B2UploadFileRequest uploadFile(String bucketId, File file) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file));
	}

	public B2UploadFileRequest uploadFile(String bucketId, File file, String mimeType) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = new B2GetUploadUrlRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse();
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file, mimeType));
	}

	public B2DeleteFileVersionResponse deleteFileVersion(String fileName, String fileId) throws B2ApiException {
		return(new B2DeleteFileVersionRequest(getB2AuthorizeAccountResponse(), fileName, fileId).getResponse());
	}

	public B2BucketResponse updateBucket(String bucketId, BucketType bucketType) throws B2ApiException {
		return(new B2UpdateBucketRequest(getB2AuthorizeAccountResponse(), bucketId, bucketType).getResponse());
	}

	public B2ListFilesResponse listFileNames(String bucketId) throws B2ApiException {
		return(new B2ListFileNamesRequest(getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

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

	private synchronized B2AuthorizeAccountResponse getB2AuthorizeAccountResponse() throws B2ApiException {
		if(null == b2AuthorizeAccountResponse) {
			b2AuthorizeAccountResponse = new B2AuthorizeAccountRequest(accountId, applicationKey).getResponse();
		}
		return(b2AuthorizeAccountResponse);
	}
}
