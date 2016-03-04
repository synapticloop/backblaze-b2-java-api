package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListBucketsResponse;

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

	public B2ListBucketsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_BUCKETS);

		requestBodyData.put(B2RequestProperties.KEY_ACCOUNT_ID, b2AuthorizeAccountResponse.getAccountId());
	}

	public B2ListBucketsResponse getResponse() throws B2Exception {
		try {
			return new B2ListBucketsResponse(EntityUtils.toString(executePost().getEntity()));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}

}
