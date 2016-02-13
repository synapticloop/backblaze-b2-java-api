package synapticloop.b2.response;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public abstract class BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseB2Response.class);

	protected static final String HEADER_X_BZ_INFO_PREFIX = "x-bz-info-";

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
	protected static final String KEY_UPLOAD_AUTH_TOKEN = "uploadAuthToken";
	protected static final String KEY_UPLOAD_URL = "uploadUrl";
	protected static final String KEY_UPLOAD_URLS = "uploadUrls";


	/**
	 * Parse a string into a JSON object 
	 * 
	 * @param response the data to parse to an object
	 * 
	 * @return the parsed JSON object
	 * 
	 * @throws B2ApiException if there was an error parsing the object
	 */
	public static JSONObject getParsedResponse(String response) throws B2ApiException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response);
		} catch (JSONException ex) {
			LOGGER.error("Could not parse response as json, data was '{}'", response);
			throw new B2ApiException(ex);
		}
		return(jsonObject);
	}

	/**
	 * Parse through the expected keys to determine whether any of the keys in 
	 * the response will not be mapped.  This will loop through the JSON object 
	 * and any key left in the object will generate a 'WARN' message.  The 
	 * response class __MUST__ remove the object (i.e. jsonObject.remove(KEY_NAME))
	 * after getting the value
	 * 
	 * @param LOGGER The logger to use
	 * @param jsonObject the parsed response as a json Object
	 */
	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys(Logger LOGGER, JSONObject jsonObject) {
		if(LOGGER.isWarnEnabled()) {
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				LOGGER.warn("Found an unexpected json key of '{}', this is not mapped to a field...", key);
			}
		}
	}

}
