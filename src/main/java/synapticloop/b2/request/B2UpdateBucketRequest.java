package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2Exception;
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

	private static final String B2_UPDATE_BUCKET = BASE_API_VERSION + "b2_update_bucket";

	public B2UpdateBucketRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, BucketType bucketType) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_UPDATE_BUCKET);

		requestBodyData.put(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_TYPE, bucketType.toString());
	}

	public B2BucketResponse getResponse() throws B2Exception {
		try {
			return(new B2BucketResponse(EntityUtils.toString(executePost().getEntity())));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
