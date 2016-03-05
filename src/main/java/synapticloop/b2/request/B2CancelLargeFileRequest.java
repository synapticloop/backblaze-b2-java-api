package synapticloop.b2.request;

import java.io.IOException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;

/**
 * <p>Cancels the upload of a large file, and deletes all of the parts that have been uploaded.</p>
 * 
 * <p>This will return an error if there is no active upload with the given file ID.</p>
 * 
 * 
 *This is the interaction class for the <strong>b2_authorize_account</strong> api 
 * calls, this was generated from the backblaze api documentation - which can be 
 * found here:
 * 
 * <a href="https://www.backblaze.com/b2/docs/b2_cancel_large_file.html">https://www.backblaze.com/b2/docs/b2_cancel_large_file.html</a>
 * 
 * @author synapticloop
 *
 */

public class B2CancelLargeFileRequest extends BaseB2Request {
	private static final String B2_CANCEL_LARGE_FILE = BASE_API + "b2_cancel_large_file";

	protected B2CancelLargeFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_CANCEL_LARGE_FILE);

		requestBodyData.put(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	public B2FileResponse getResponse() throws B2ApiException {
		try {
			return(new B2FileResponse(EntityUtils.toString(executePost().getEntity())));
		} catch(IOException e) {
			throw new B2ApiException(e);
		}
	}
}
