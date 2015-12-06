package synapticloop.b2.request;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Creates a new bucket. A bucket belongs to the account used to create it.</p>
 * 
 * <p>Buckets can be named. The name must be globally unique. No account can use a bucket with the same name. Buckets are assigned a unique bucketId which is used when uploading, downloading, or deleting files.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_create_bucket</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_create_bucket.html">http://www.backblaze.com/b2/docs/b2_create_bucket.html</a>
 * 
 * @author synapticloop
 */

public class B2CreateBucketRequest extends BaseB2Request {
	private static final String B2_CREATE_BUCKET = BASE_API_VERSION + "b2_create_bucket";

	public B2CreateBucketRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, BucketType bucketType) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_CREATE_BUCKET;

		data.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		data.put(KEY_BUCKET_NAME, bucketName);
		data.put(KEY_BUCKET_TYPE, bucketType.toString());
	}

	public B2BucketResponse getResponse() throws B2ApiException {
		return(new B2BucketResponse(executePost()));
	}
}
