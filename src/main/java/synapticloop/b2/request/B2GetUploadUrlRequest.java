package synapticloop.b2.request;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;

/**
 * <p>Gets an URL to use for uploading files.</p>
 * 
 * <p>When you upload a file to B2, you must call b2_get_upload_url first to get the URL for uploading directly to the place where the file will be stored.</p>
 * <p>TODO: Describe how you know when to get a new upload URL.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_get_upload_url</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_get_upload_url.html">http://www.backblaze.com/b2/docs/b2_get_upload_url.html</a>
 * 
 * @author synapticloop
 */

public class B2GetUploadUrlRequest extends BaseB2Request {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2GetUploadUrlRequest.class);
	private static final String B2_GET_UPLOAD_URL = BASE_API_VERSION + "b2_get_upload_url";

	public B2GetUploadUrlRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL;

		stringData.put(KEY_BUCKET_ID, bucketId);
	}

	public B2GetUploadUrlRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Map<String, String> fileInfo) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_GET_UPLOAD_URL;

		stringData.put(KEY_BUCKET_ID, bucketId);

		// TODO - need to do different
		// now go through the fileInfo Object and add the headers
		Iterator<String> iterator = fileInfo.keySet().iterator();
		while (iterator.hasNext()) {
			String headerKey = (String) iterator.next();
			headers.put(HEADER_X_BZ_INFO_PREFIX + headerKey, fileInfo.get(headerKey));
		}
	}

	public B2GetUploadUrlResponse getResponse() throws B2ApiException {
		return(new B2GetUploadUrlResponse(executePost(LOGGER)));
	}
}
