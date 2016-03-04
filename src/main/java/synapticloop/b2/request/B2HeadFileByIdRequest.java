package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2DownloadFileResponse;

/**
 * <p>Downloads one file from B2.</p>
 * 
 * <p>The response contains the following headers, which contain the same information they did when the file was uploaded:</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_download_file_by_id</strong> 
 * api calls, this was generated from the backblaze api documentation - which 
 * can be found here:
 * 
 * <a href="http://www.backblaze.com/b2/docs/b2_download_file_by_id.html">http://www.backblaze.com/b2/docs/b2_download_file_by_id.html</a>
 * 
 * @author synapticloop
 */
public class B2HeadFileByIdRequest extends BaseB2Request {
	private static final String B2_DOWNLOAD_FILE_BY_ID = BASE_API_VERSION + "b2_download_file_by_id";

	public B2HeadFileByIdRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String fileId) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getDownloadUrl() + B2_DOWNLOAD_FILE_BY_ID);
		requestParameters.put(B2RequestProperties.KEY_FILE_ID, fileId);
	}

	/**
	 * Return the response for the HEAD request 
	 * 
	 * @return the download file response - note that this does not contain any body content
	 * 
	 * @throws B2Exception if something went wrong
	 */
	public B2DownloadFileResponse getResponse() throws B2Exception {
		return(new B2DownloadFileResponse(this.executeHead()));
	}
}
