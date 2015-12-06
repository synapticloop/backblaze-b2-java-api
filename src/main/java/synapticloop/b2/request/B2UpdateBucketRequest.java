package synapticloop.b2.request;

import java.util.HashMap;
import java.util.Map;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Update an existing bucket.</p>
 * 
 * <p>Modifies the bucketType of an existing bucket. Can be used to allow everyone to download the contents of the bucket without providing any authorization, or to prevent anyone from downloading the contents of the bucket without providing a bucket auth token.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_update_bucket</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_update_bucket.html">http://www.backblaze.com/b2/docs/b2_update_bucket.html</a>
 * 
 * @author synapticloop
 */

public class B2UpdateBucketRequest extends BaseB2Request {

	private static final String B2_UPDATE_BUCKET = "/b2api/v1/b2_update_bucket";
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private String bucketId = null;
	private String bucketType = null;

	public B2UpdateBucketRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, BucketType bucketType) {
		super(b2AuthorizeAccountResponse);
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
		this.bucketId = bucketId;
		this.bucketType = bucketType.toString();
	}
	
	public B2BucketResponse getResponse() throws B2ApiException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		map.put(KEY_BUCKET_ID, bucketId);
		map.put(KEY_BUCKET_TYPE, bucketType);

		return(new B2BucketResponse(executePost(b2AuthorizeAccountResponse, B2_UPDATE_BUCKET, map)));
	}
}
