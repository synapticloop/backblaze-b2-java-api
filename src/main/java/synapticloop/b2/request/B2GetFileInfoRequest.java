package synapticloop.b2.request;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2FileResponse;

/**
 * <p>Gets information about one file stored in B2.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_get_file_info</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_get_file_info.html">http://www.backblaze.com/b2/docs/b2_get_file_info.html</a>
 * 
 * @author synapticloop
 */

public class B2GetFileInfoRequest extends BaseB2Request {

	private static final String B2_GET_FILE_INFO = BASE_API_VERSION + "b2_get_file_info";

	public B2GetFileInfoRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(b2AuthorizeAccountResponse);

		url = b2AuthorizeAccountResponse.getApiUrl() + B2_GET_FILE_INFO;

		data.put(KEY_FILE_ID, fileId);
	}

	public B2FileResponse getResponse() throws B2ApiException {
		return(new B2FileResponse(executePost()));
	}
}
