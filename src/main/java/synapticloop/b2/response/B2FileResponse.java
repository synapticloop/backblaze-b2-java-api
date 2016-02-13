package synapticloop.b2.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2FileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileResponse.class);

	private String fileId = null;
	private String fileName = null;
	private String accountId = null;
	private String bucketId = null;
	private long contentLength = -1l;
	private String contentSha1 = null;
	private String contentType = null;
	private Map<String, Object> fileInfo = new HashMap<String, Object>();

	@SuppressWarnings("rawtypes")
	public B2FileResponse(String response) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(response);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		jsonObject.remove(KEY_FILE_ID);

		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		jsonObject.remove(KEY_FILE_NAME);

		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		jsonObject.remove(KEY_ACCOUNT_ID);

		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		jsonObject.remove(KEY_BUCKET_ID);

		this.contentLength = jsonObject.optLong(KEY_CONTENT_LENGTH);
		jsonObject.remove(KEY_CONTENT_LENGTH);

		this.contentSha1 = jsonObject.optString(KEY_CONTENT_SHA1);
		jsonObject.remove(KEY_CONTENT_SHA1);

		this.contentType = jsonObject.optString(KEY_CONTENT_TYPE);
		jsonObject.remove(KEY_CONTENT_TYPE);


		JSONObject fileInfoObject = jsonObject.optJSONObject(KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.opt(key));
			}
		}
		jsonObject.remove(KEY_FILE_INFO);

		warnOnMissedKeys(LOGGER, jsonObject);

	}

	/**
	 * Get the id of the file that was operated on
	 * 
	 * @return the id of the file that was operated on
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of the file that was operated on
	 * 
	 * @return the name of the file that was operated on
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Get the id of the account that was operated on
	 * 
	 * @return the id of the account that was operated on
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Get the id of the bucket that was operated on
	 * 
	 * @return the id of the bucket that was operated on
	 */
	public String getBucketId() { return this.bucketId; }

	/**
	 * Get content length of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content length of the file that was operated on
	 */
	public long getContentLength() { return this.contentLength; }

	/**
	 * Get content SHA1 of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content SHA1 of the file that was operated on
	 */
	public String getContentSha1() { return this.contentSha1; }

	/**
	 * Get content type of the file that was operated on, or null if not returned 
	 * in the response
	 * 
	 * @return the content type of the file that was operated on
	 */
	public String getContentType() { return this.contentType; }

	/**
	 * Get the map of the file info for the file that was operated on, or an empty 
	 * map if not set.
	 * 
	 * @return the map of the file info for the file that was operated on
	 */
	public Map<String, Object> getFileInfo() { return this.fileInfo; }
}
