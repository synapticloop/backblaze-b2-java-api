package synapticloop.b2.response;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public abstract class BaseB2Response {
	protected static final String KEY_ACCOUNT_ID = "accountId";
	protected static final String KEY_ACTION = "action";
	protected static final String KEY_API_URL = "apiUrl";
	protected static final String KEY_AUTHORIZATION_TOKEN = "authorizationToken";
	protected static final String KEY_BUCKET_ID = "bucketId";
	protected static final String KEY_BUCKET_NAME = "bucketName";
	protected static final String KEY_BUCKET_TYPE = "bucketType";
	protected static final String KEY_CONTENT_LENGTH = "contentLength";
	protected static final String KEY_CONTENT_SHA1 = "contentSha1";
	protected static final String KEY_CONTENT_TYPE = "contentType";
	protected static final String KEY_DOWNLOAD_URL = "downloadUrl";
	protected static final String KEY_FILE_ID = "fileId";
	protected static final String KEY_FILE_INFO = "fileInfo";
	protected static final String KEY_FILE_NAME = "fileName";
	protected static final String KEY_FILES = "files";
	protected static final String KEY_NEXT_FILE_ID = "nextFileId";
	protected static final String KEY_NEXT_FILE_NAME = "nextFileName";
	protected static final String KEY_SIZE = "size";
	protected static final String KEY_UPLOAD_TIMESTAMP = "uploadTimestamp";
	protected static final String KEY_UPLOAD_URL = "uploadUrl";

	public static JSONObject getParsedResponse(String data) throws B2ApiException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
		} catch (JSONException ex) {
			throw new B2ApiException(ex);
		}
		return(jsonObject);
	}
}
