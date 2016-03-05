package synapticloop.b2.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2StartLargeFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2StartLargeFileResponse.class);

	private String fileId = null;
	private String fileName = null;
	private String accountId = null;
	private String bucketId = null;
	private String contentType = null;
	private Map<String, Object> fileInfo = new HashMap<String, Object>();
	private String uploadAuthToken = null;
	private List<String> uploadUrls = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	public B2StartLargeFileResponse(String json) throws B2ApiException {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID);

		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME);

		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID);

		this.contentType = response.optString(B2ResponseProperties.KEY_CONTENT_TYPE);

		JSONObject fileInfoObject = response.optJSONObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.optString(key, null));
			}
		}
	
		this.uploadAuthToken = response.optString(B2ResponseProperties.KEY_UPLOAD_AUTH_TOKEN);

		// TODO - confirm whether this actually returns an array...
		JSONArray uploadUrlsArray = response.optJSONArray(B2ResponseProperties.KEY_UPLOAD_URLS);
		for(int i = 0; i < uploadUrlsArray.length(); i++) {
			this.uploadUrls.add(uploadUrlsArray.optString(i));
		}

		if(LOGGER.isDebugEnabled()) {
			response.remove(B2ResponseProperties.KEY_FILE_ID);
			response.remove(B2ResponseProperties.KEY_FILE_NAME);
			response.remove(B2ResponseProperties.KEY_ACCOUNT_ID);
			response.remove(B2ResponseProperties.KEY_BUCKET_ID);
			response.remove(B2ResponseProperties.KEY_CONTENT_TYPE);
			response.remove(B2ResponseProperties.KEY_FILE_INFO);
			response.remove(B2ResponseProperties.KEY_UPLOAD_AUTH_TOKEN);
			response.remove(B2ResponseProperties.KEY_UPLOAD_URLS);

			warnOnMissedKeys(LOGGER, response);
		}
	}

	public String getBucketId() { return this.bucketId; }

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public String getAccountId() { return this.accountId; }

	public String getContentType() { return this.contentType; }

	public Map<String, Object> getFileInfo() { return this.fileInfo; }

	public String getUploadAuthToken() { return this.uploadAuthToken; }

	public List<String> getUploadUrls() { return this.uploadUrls; }

}
