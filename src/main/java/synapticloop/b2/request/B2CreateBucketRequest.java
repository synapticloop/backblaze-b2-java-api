package synapticloop.b2.request;

import java.util.HashMap;
import java.util.Map;

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
	private static final String B2_CREATE_BUCKET = "/b2api/v1/b2_create_bucket";
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private String bucketName = null;
	private String bucketType = null;

	public B2CreateBucketRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketName, BucketType bucketType) {
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
		this.bucketName = bucketName;
		this.bucketType = bucketType.toString();
	}

	public B2BucketResponse getResponse() throws B2ApiException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		map.put(KEY_BUCKET_NAME, bucketName);
		map.put(KEY_BUCKET_TYPE, bucketType);

		return(new B2BucketResponse(executePost(b2AuthorizeAccountResponse, B2_CREATE_BUCKET, map)));
	}
}
