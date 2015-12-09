package synapticloop.b2.request;

import java.util.ArrayList;
import java.util.List;

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
 * This is the interaction class for the <strong>b2_list_buckets</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_list_buckets.html">http://www.backblaze.com/b2/docs/b2_list_buckets.html</a>
 * 
 * @author synapticloop
 */

public class B2ListBucketsRequest extends BaseB2Request {
	private static final String B2_LIST_BUCKETS = BASE_API_VERSION + "b2_list_buckets";

	public B2ListBucketsRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_BUCKETS;

		stringData.put(KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
	}

	public List<B2BucketResponse> getResponse() throws B2ApiException {
		List<B2BucketResponse> responses = new ArrayList<B2BucketResponse>();

		JSONObject jsonObject = BaseB2Response.getParsedResponse(executePost());

		JSONArray optJSONArray = jsonObject.optJSONArray("buckets");
		for(int i = 0; i < optJSONArray.length(); i++) {
			responses.add(new B2BucketResponse(optJSONArray.optJSONObject(i)));
		}

		return(responses);
	}
}
