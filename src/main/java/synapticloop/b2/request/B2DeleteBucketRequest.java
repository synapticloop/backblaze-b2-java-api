package synapticloop.b2.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

/**
 * <p>Deletes the bucket specified. Only buckets that contain no version of any files can be deleted.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_delete_bucket</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_delete_bucket.html">http://www.backblaze.com/b2/docs/b2_delete_bucket.html</a>
 * 
 * @author synapticloop
 */

public class B2DeleteBucketRequest extends BaseB2Request {
	private static final String B2_DELETE_BUCKET = "/b2api/v1/b2_delete_bucket";
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private String bucketId = null;

	public B2DeleteBucketRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
		this.bucketId = bucketId;

	}

	public B2BucketResponse getResponse() throws B2ApiException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
			map.put(KEY_BUCKET_ID, bucketId);

			connection = getApiPostConnection(B2_DELETE_BUCKET, b2AuthorizeAccountResponse);
			inputStream = writePostData(connection, map);

			return(new B2BucketResponse(inputStream));
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			tidyUp(inputStream, connection);
		}
	}
}
