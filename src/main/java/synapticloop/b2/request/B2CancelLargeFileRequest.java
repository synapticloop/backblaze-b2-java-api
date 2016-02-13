package synapticloop.b2.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(B2CancelLargeFileRequest.class);
	private static final String B2_CANCEL_LARGE_FILE = BASE_API + "b2_cancel_large_file";

	protected B2CancelLargeFileRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_CANCEL_LARGE_FILE;

		requestBodyStringData.put(KEY_FILE_ID, fileId);
	}

	public B2FileResponse getResponse() throws B2ApiException {
		return(new B2FileResponse(executePost(LOGGER)));
	}
}
