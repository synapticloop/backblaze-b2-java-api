package synapticloop.b2.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.request.B2AuthorizeAccountRequest;
import synapticloop.b2.request.B2CreateBucketRequest;
import synapticloop.b2.request.B2DeleteBucketRequest;
import synapticloop.b2.request.B2DeleteFileVersionRequest;
import synapticloop.b2.request.B2GetUploadUrlRequest;
import synapticloop.b2.request.B2UploadFileRequest;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;

public class B2TestHelper {
	public static final String B2_BUCKET_PREFIX = "b2api-test-";
	public static final String B2_ACCOUNT_ID = "B2_ACCOUNT_ID";
	public static final String B2_APPLICATION_KEY = "B2_APPLICATION_KEY";

	public static final String DUMMY_FILE_CONTENT = "hello world!";

	private static B2AuthorizeAccountResponse response = null;

	public static B2AuthorizeAccountResponse getB2AuthorizeAccountResponse() throws B2ApiException {

		if(null == response) {
			boolean isOK = true;
			String b2AccountId = System.getenv(B2_ACCOUNT_ID);
			String b2ApplicationKey = System.getenv(B2_APPLICATION_KEY);

			if(null == b2AccountId) {
				System.err.println("Could not find the environment variable '" + B2_ACCOUNT_ID + "', cannot continue with tests, exiting...");
				isOK = false;
			}

			if(null == b2ApplicationKey) {
				System.err.println("Could not find the environment variable '" + B2_APPLICATION_KEY + "', cannot continue with tests, exiting...");
				isOK = false;
			}

			if(!isOK) {
				System.exit(-1);
			}

			B2AuthorizeAccountRequest b2AuthorizeAccountRequest = new B2AuthorizeAccountRequest(b2AccountId, b2ApplicationKey);
			response = b2AuthorizeAccountRequest.getResponse();
		}
		return(response);
	}

	/**
	 * Create a random private bucket with the name `B2_BUCKET_PREFIX` and a 
	 * random UUID
	 * 
	 * @return the B2BucketResponse
	 * 
	 * @throws B2ApiException if there was an error with the creation of the bucket
	 */
	public static B2BucketResponse createRandomPrivateBucket() throws B2ApiException {
		B2CreateBucketRequest b2CreateBucketRequest = new B2CreateBucketRequest(getB2AuthorizeAccountResponse(), B2_BUCKET_PREFIX + UUID.randomUUID().toString(), BucketType.ALL_PRIVATE);
		return(b2CreateBucketRequest.getResponse());
	}


	/**
	 * Create a random public bucket with the name `B2_BUCKET_PREFIX` and a 
	 * random UUID
	 * 
	 * @return the B2BucketResponse
	 * 
	 * @throws B2ApiException if there was an error with the creation of the bucket
	 */
	public static B2BucketResponse createRandomPublicBucket() throws B2ApiException {
		B2CreateBucketRequest b2CreateBucketRequest = new B2CreateBucketRequest(getB2AuthorizeAccountResponse(), B2_BUCKET_PREFIX + UUID.randomUUID().toString(), BucketType.ALL_PUBLIC);
		return(b2CreateBucketRequest.getResponse());
	}

	/**
	 * Delete a bucker by its id
	 * 
	 * @param bucketId the bucket id to delete
	 * 
	 * @return the deleted bucket response
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public static B2BucketResponse deleteBucket(String bucketId) throws B2ApiException {
		B2DeleteBucketRequest b2DeleteBucketRequest = new B2DeleteBucketRequest(getB2AuthorizeAccountResponse(), bucketId);
		return(b2DeleteBucketRequest.getResponse());
	}

	public static B2GetUploadUrlResponse getUploadUrl(String bucketId) throws B2ApiException {
		return(new B2GetUploadUrlRequest(B2TestHelper.getB2AuthorizeAccountResponse(), bucketId).getResponse());
	}

	public static B2DeleteFileVersionResponse deleteFile(String fileName, String fileId) throws B2ApiException {
		return(new B2DeleteFileVersionRequest(getB2AuthorizeAccountResponse(), fileName, fileId).getResponse());
	}

	/**
	 * Create a temporary file into a bucket
	 * 
	 * @param bucketId the bucket to create the file in
	 * 
	 * @return the file response
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public static B2FileResponse uploadTemporaryFileToBucket(String bucketId) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = getUploadUrl(bucketId);
		File file = null;
		try {
			file = File.createTempFile("backblaze-api-test", ".txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(DUMMY_FILE_CONTENT);
			fileWriter.flush();
			fileWriter.close();
			file.deleteOnExit();
		} catch(IOException ioex) {
			throw new B2ApiException("Could not create temporary file", ioex);
		}
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file.getName(), file).getResponse());
	}

	/**
	 * Create a temporary file into a bucket
	 * 
	 * @param bucketId the bucket to create the file in
	 * 
	 * @return the file response
	 * 
	 * @throws B2ApiException if something went wrong
	 */
	public static B2FileResponse uploadTemporaryFileToBucket(String bucketId, Map<String, String> fileInfo) throws B2ApiException {
		B2GetUploadUrlResponse b2GetUploadUrlResponse = getUploadUrl(bucketId);
		File file = null;
		try {
			file = File.createTempFile("backblaze-api-test", ".txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(DUMMY_FILE_CONTENT);
			fileWriter.flush();
			fileWriter.close();
			file.deleteOnExit();
		} catch(IOException ioex) {
			throw new B2ApiException("Could not create temporary file", ioex);
		}
		return(new B2UploadFileRequest(getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file.getName(), file, fileInfo).getResponse());
	}

}
