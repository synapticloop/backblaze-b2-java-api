package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import synapticloop.b2.exception.B2Exception;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;
import synapticloop.b2.util.Helper;

/**
 * <p>Lists the names of all files in a bucket, starting at a given name.</p>
 * 
 * <p>This call returns at most 1000 file names, but it can be called repeatedly to scan through all of the file names in a bucket. Each time you call, it returns an "endFileName" that can be used as the starting point for the next call.</p>
 * <p>There may be many file versions for the same name, but this call will return each name only once. If you want all of the versions, use b2_list_file_versions instead.</p>
 * <p>To go through all of the file names in a bucket, use a loop like this:</p>
 * 
 * 
 * This is the interaction class for the <strong>b2_list_file_names</strong> api calls, this was 
 * generated from the backblaze api documentation - which can be found here:
 * <a href="http://www.backblaze.com/b2/docs/b2_list_file_names.html">http://www.backblaze.com/b2/docs/b2_list_file_names.html</a>
 * 
 * @author synapticloop
 */
public class B2ListFileNamesRequest extends BaseB2Request {
	private static final String B2_LIST_FILE_NAMES = BASE_API_VERSION + "b2_list_file_versions";

	private static final int DEFAULT_MAX_FILE_COUNT = 100;

	public B2ListFileNamesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) throws B2Exception {
		this(client, b2AuthorizeAccountResponse, bucketId, null, DEFAULT_MAX_FILE_COUNT);
	}

	public B2ListFileNamesRequest(CloseableHttpClient client, B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String startFileName, Integer maxFileCount) throws B2Exception {
		super(client, b2AuthorizeAccountResponse, b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_FILE_NAMES);

		requestBodyData.put(B2RequestProperties.KEY_BUCKET_ID, bucketId);
		if(null != startFileName) {
			requestBodyData.put(B2RequestProperties.KEY_START_FILE_NAME, Helper.urlEncode(startFileName));
		}
		if(maxFileCount > MAX_FILE_COUNT_RETURN) {
			throw new B2Exception("Maximum return file count is " + MAX_FILE_COUNT_RETURN);
		}
		requestBodyData.put(B2RequestProperties.KEY_MAX_FILE_COUNT, maxFileCount);
	}

	public B2ListFilesResponse getResponse() throws B2Exception {
		try {
			return(new B2ListFilesResponse(EntityUtils.toString(executePost().getEntity())));
		}
		catch(IOException e) {
			throw new B2Exception(e);
		}
	}
}
