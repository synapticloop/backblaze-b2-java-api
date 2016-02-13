package synapticloop.b2.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Deletes the bucket specified. Only buckets that contain no version of any files can be deleted.</p>
 * 
 * This is the interaction class for the <strong>b2_delete_bucket</strong> api 
 * calls, this was generated from the backblaze api documentation - which can 
 * be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_delete_bucket.html">http://www.backblaze.com/b2/docs/b2_delete_bucket.html</a>
 * 
 * @author synapticloop
 */

public class B2DeleteBucketRequest extends BaseB2Request {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DeleteBucketRequest.class);
	private static final String B2_DELETE_BUCKET = BASE_API_VERSION + "b2_delete_bucket";

	/**
	 * Instantiate a new delete bucket request
	 * 
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * 
	 * @param bucketId the id of the bucket to delete
	 */
	public B2DeleteBucketRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_DELETE_BUCKET;

		requestBodyStringData.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		requestBodyStringData.put(KEY_BUCKET_ID, bucketId);
	}

	/**
	 * return the deleted bucket response
	 * 
	 * @return The deleted bucket response
	 * 
	 * @throws B2ApiException if there was an error with the call, or if you are 
	 *     trying to delete a bucket which is not empty
	 */
	public B2BucketResponse getResponse() throws B2ApiException {
		return(new B2BucketResponse(executePost(LOGGER)));
	}
}
