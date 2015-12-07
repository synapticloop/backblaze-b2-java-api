package synapticloop.b2.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2FileResponse extends BaseB2Response {
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
		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		this.contentLength = jsonObject.optLong(KEY_CONTENT_LENGTH);
		this.contentSha1 = jsonObject.optString(KEY_CONTENT_SHA1);
		this.contentType = jsonObject.optString(KEY_CONTENT_TYPE);

		JSONObject fileInfoObject = jsonObject.optJSONObject(KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.opt(key));
			}
		}
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public String getAccountId() { return this.accountId; }
	public String getBucketId() { return this.bucketId; }
	public long getContentLength() { return this.contentLength; }
	public String getContentSha1() { return this.contentSha1; }
	public String getContentType() { return this.contentType; }
	public Map<String, Object> getFileInfo() { return this.fileInfo; }
}
