package synapticloop.b2.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DeleteFileVersionRequest.class);
	private static final String B2_DELETE_FILE_VERSION = BASE_API_VERSION + "b2_delete_file_version";

	/**
	 * Instantiate a delete file version request
	 * 
	 * @param b2AuthorizeAccountResponse the authorize account response
	 * @param fileName the name of the file to delete
	 * @param fileId The ID of the file, as returned by {@link B2UploadFileRequest}, 
	 *     {@link B2ListFileNamesRequest}, or {@link B2ListFileVersionsRequest}..
	 */
	public B2DeleteFileVersionRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileName, String fileId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_DELETE_FILE_VERSION;

		stringData.put(KEY_FILE_NAME, fileName);
		stringData.put(KEY_FILE_ID, fileId);
	}

	/**
	 * Return the http response for the call
	 * 
	 * @return the delete file version response
	 * 
	 * @throws B2ApiException if there was an error with the call
	 */
	public B2DeleteFileVersionResponse getResponse() throws B2ApiException {
		return(new B2DeleteFileVersionResponse(executePost(LOGGER)));
	}
}
