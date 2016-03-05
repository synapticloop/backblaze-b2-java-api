package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;
import synapticloop.b2.util.Helper;

/**
 * <p>Lists all of the versions of all of the files contained in one bucket, in alphabetical order by file name, and by reverse of date/time uploaded for versions of files with the same name.</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_list_file_versions</strong> api calls, this was
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_list_file_versions.html">http://www.backblaze.com/b2/docs/b2_list_file_versions.html</a>
 * 
 * @author synapticloop
 */
public class B2ListFileVersionsRequest extends BaseB2Request {
	private static final String B2_LIST_FILE_VERSIONS = BASE_API_VERSION + "b2_list_file_versions";

	private static final int DEFAULT_MAX_FILE_COUNT = 100;

	public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) throws B2Exception {
		this(client, b2AuthorizeAccountResponse, bucketId, DEFAULT_MAX_FILE_COUNT);
	}

	public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Integer maxFileCount) throws B2Exception {
		this(client, b2AuthorizeAccountResponse, bucketId, maxFileCount, null, null);
	}

	public B2ListFileVersionsRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, Integer maxFileCount, String startFileName, String startFileId) throws B2Exception {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_FILE_VERSIONS);
		if(null != startFileId) {
			if(null == startFileName) {
				throw new B2Exception(String.format("Must include a '%s', if you are also include a '%s'.",
						B2RequestProperties.KEY_START_FILE_NAME, B2RequestProperties.KEY_START_FILE_ID));
			}
		}
		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		if(maxFileCount > MAX_FILE_COUNT_RETURN) {
			throw new B2Exception(String.format("Maximum return file count is %d", MAX_FILE_COUNT_RETURN));
		}
		requestBodyData.put(B2RequestProperties.KEY_MAX_FILE_COUNT, maxFileCount);
		if(null != startFileName) {
			requestBodyData.put(B2RequestProperties.KEY_START_FILE_NAME, Helper.urlEncode(startFileName));
		}
		if(null != startFileId) {
			requestBodyData.put(B2RequestProperties.KEY_START_FILE_ID, startFileId);
		}
	}

	public B2ListFilesResponse getResponse() throws B2Exception {
		try {
			return new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity()));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
