package synapticloop.b2.request;

import synapticloop.b2.response.B2AuthorizeAccountResponse;

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

public class B2HideFile extends BaseB2Request {
	private static final String B2_HIDE_FILE = BASE_API_VERSION + "b2_hide_file";

	public B2HideFile(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String fileName) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_HIDE_FILE;

		data.put(KEY_BUCKET_ID, bucketId);
		data.put(KEY_FILE_NAME, fileName);
	}
	
	public void getResponse() {
//		return(new )
	}
}
