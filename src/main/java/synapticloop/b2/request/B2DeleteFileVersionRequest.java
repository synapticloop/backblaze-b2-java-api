package synapticloop.b2.request;

import java.util.HashMap;
import java.util.Map;

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
	private static final String B2_DELETE_FILE_VERSION = "/b2api/v1/b2_delete_file_version";
	Map<String, String> map = new HashMap<String, String>();

	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private String fileName = null;
	private String fileId = null;

	public B2DeleteFileVersionRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName, String fileId) {
		super(b2AuthorizeAccountResponse);
		this.b2AuthorizeAccountResponse = b2AuthorizeAccountResponse;
		this.fileName = fileName;
		this.fileId = fileId;
	}

	public B2DeleteFileVersionResponse getResponse() throws B2ApiException {
		Map<String, String> map = new HashMap<String, String>();
		map.put(KEY_FILE_NAME, fileName);
		map.put(KEY_FILE_ID, fileId);

		return(new B2DeleteFileVersionResponse(executePost(b2AuthorizeAccountResponse, B2_DELETE_FILE_VERSION, map)));
	}
}
