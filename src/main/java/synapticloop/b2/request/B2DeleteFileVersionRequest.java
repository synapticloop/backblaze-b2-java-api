package synapticloop.b2.request;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;

/**
 * <p>Deletes one version of a file from B2.</p>
 * 
 * <p>If the version you delete is the latest version, and there are older versions, then the most recent older version will become the current version, and be the one that you'll get when downloading by name. See the File Versions page for more details.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_delete_file_version</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_delete_file_version.html">http://www.backblaze.com/b2/docs/b2_delete_file_version.html</a>
 * 
 * @author synapticloop
 */

public class B2DeleteFileVersionRequest extends BaseB2Request {
	private static final String B2_DELETE_FILE_VERSION = BASE_API_VERSION + "b2_delete_file_version";

	public B2DeleteFileVersionRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName, String fileId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_DELETE_FILE_VERSION;

		stringData.put(KEY_FILE_NAME, fileName);
		stringData.put(KEY_FILE_ID, fileId);
	}

	public B2DeleteFileVersionResponse getResponse() throws B2ApiException {
		return(new B2DeleteFileVersionResponse(executePost()));
	}
}
