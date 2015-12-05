package synapticloop.b2.request;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.BaseB2Response;

/**
 * <p>Lists buckets associated with an account, in alphabetical order by bucket ID.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_list_buckets</strong> api calls, this was automatically
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_list_buckets.html">http://www.backblaze.com/b2/docs/b2_list_buckets.html</a>
 * 
 * @author synapticloop
 */

public class B2ListBucketsRequest extends BaseB2Request {
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;

	public B2ListBucketsRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse) {
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
	}

	public List<B2BucketResponse> getResponse() throws B2ApiException {
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		List<B2BucketResponse> responses = new ArrayList<B2BucketResponse>();

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountId", b2AuthorizeAccountResponse.getAccountId());

			connection = getApiPostConnection("/b2api/v1/b2_list_buckets", b2AuthorizeAccountResponse);
			inputStream = writePostData(connection, map);

			JSONObject jsonObject = BaseB2Response.getParsedResponse(inputStream);
			JSONArray optJSONArray = jsonObject.optJSONArray("buckets");
			for(int i = 0; i < optJSONArray.length(); i++) {
				responses.add(new B2BucketResponse(optJSONArray.optJSONObject(i)));
			}

			return(responses);
		} catch (IOException ex) {
			throw new B2ApiException(ex);
		} finally {
			tidyUp(inputStream, connection);
		}
	}
}
