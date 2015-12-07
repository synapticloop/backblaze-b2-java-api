package synapticloop.b2.request;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2ListFilesResponse;

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

	private int maxFileCount = 100;

	public B2ListFileVersionsRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId) {
		super(b2AuthorizeAccountResponse);
		url = b2AuthorizeAccountResponse.getApiUrl() + B2_LIST_FILE_VERSIONS;

		data.put(KEY_BUCKET_ID, bucketId);
		data.put(KEY_MAX_FILE_COUNT, maxFileCount);
	}

	public B2ListFileVersionsRequest(B2AuthorizeAccountResponse b2AuthorizeAccountResponse, String bucketId, String startFileName, String startFileId, Integer maxFileCount) {
		this(b2AuthorizeAccountResponse, bucketId);

		data.put(KEY_BUCKET_ID, bucketId);
		if(null != startFileName) {
			data.put(KEY_START_FILE_NAME, startFileName);
		}

		if(null != startFileId) {
			data.put(KEY_START_FILE_ID, startFileId);
		}

		if(null != maxFileCount) {
			data.put(KEY_MAX_FILE_COUNT, maxFileCount);
		}
	}

	public B2ListFilesResponse getResponse() throws B2ApiException {
		if(maxFileCount > MAX_FILE_COUNT_RETURN) {
			throw new B2ApiException("Maximum return file count is " + MAX_FILE_COUNT_RETURN);
		}

		if(null != data.get(KEY_START_FILE_ID) && null == data.get(KEY_START_FILE_NAME)) {
			throw new B2ApiException("You __MUST__ include a '" + KEY_START_FILE_NAME + "', if you are also include a '" + KEY_START_FILE_ID + "'.");
		}

		return(new B2ListFilesResponse(executePost()));
	}
}
