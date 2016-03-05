package synapticloop.b2.request;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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

public class B2StartLargeFileRequest extends BaseB2Request {
	private static final String B2_START_LARGE_FILE = BASE_API + "b2_start_large_file";

	/**
	 * Create a new B2 Start large file request
	 * 
	 * @param b2AuthorizeAccountResponse the authorise account response
	 * @param bucketId the id of the bucket to upload to
	 * @param fileName the name of the file
	 * @param mimeType the mimeType (optional, will default to 'b2/x-auto' which 
	 *     backblaze will attempt to determine automatically)
	 * @param fileInfo the file info map which are passed through as key value
	 *     pairs in a jsonObject named 'fileInfo'
	 */
	protected B2StartLargeFileRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String fileName, String mimeType, Map<String, String> fileInfo) {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_START_LARGE_FILE);

		
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		requestBodyData.put(B2RequestProperties.KEY_FILE_NAME, fileName);
		if(null != mimeType) {
			requestBodyData.put(B2RequestProperties.KEY_MIME_TYPE, mimeType);
		} else {
			requestBodyData.put(B2RequestProperties.KEY_MIME_TYPE, B2RequestProperties.VALUE_B2_X_AUTO);
		}

		// now go through and add in the 'X-Bz-Info-*' headers
		if(null != fileInfo) {
			Iterator<String> iterator = fileInfo.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				requestHeaders.put(key, fileInfo.get(key));
			}
		}
	}

	public B2FileResponse getResponse() throws B2ApiException {
		try {
			return(new B2FileResponse(EntityUtils.toString(executePost().getEntity())));
		} catch(IOException e) {
			throw new B2ApiException(e);
		}
	}

}
