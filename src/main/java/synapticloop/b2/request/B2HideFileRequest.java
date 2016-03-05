package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2HideFileResponse;
import synapticloop.b2.util.Helper;

/**
 * <p>Hides a file so that downloading by name will not find the file, but previous versions of the file are still stored. See File Versions about what it means to hide a file.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_hide_file</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_hide_file.html">http://www.backblaze.com/b2/docs/b2_hide_file.html</a>
 * 
 * @author synapticloop
 */
public class B2HideFileRequest extends BaseB2Request {
	private static final String B2_HIDE_FILE = BASE_API_VERSION + "b2_hide_file";

	public B2HideFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String fileName) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_HIDE_FILE);

		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		requestBodyData.put(B2RequestProperties.KEY_FILE_NAME, Helper.urlEncode(fileName));
	}

	public B2HideFileResponse getResponse() throws B2Exception {
		try {
			return(new B2HideFileResponse(EntityUtils.toString(executePost().getEntity())));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
