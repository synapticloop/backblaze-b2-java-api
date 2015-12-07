package synapticloop.b2.request;

import java.io.File;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file from B2.</p>
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_id</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_id.html">http://www.backblaze.com/b2/docs/b2_download_file_by_id.html</a>
 * 
 * @author synapticloop
 */

public class B2DownloadFileByIdRequest extends BaseB2Request {
	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";
	private File fileTo = null;

	public B2DownloadFileByIdRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID;
		parameters.put(KEY_FILE_ID, fileId);
	}

	public B2DownloadFileByIdRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId, File fileTo) {
		this(b2AuthorizeAccountResponse, fileId);
		this.fileTo = fileTo;
	}

	public B2DownloadFileResponse getResponse() throws B2ApiException {
		return(new B2DownloadFileResponse(executeGetWithData(), fileTo));
	}
}
