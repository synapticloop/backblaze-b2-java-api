package synapticloop.b2.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2Exception;

public class B2FileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileResponse.class);

	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final Long contentLength;
	private final String contentSha1;
	private final String contentType;
	private final Map<String, String> fileInfo;

	@SuppressWarnings("rawtypes")
	public B2FileResponse(String json) throws B2Exception {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.contentLength = response.optLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = response.optString(B2ResponseProperties.KEY_CONTENT_SHA1, null);
		this.contentType = response.optString(B2ResponseProperties.KEY_CONTENT_TYPE, null);

		this.fileInfo = new HashMap<String, String>();

		JSONObject fileInfoObject = response.optJSONObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.optString(key, null));
			}
		}

		if(LOGGER.isWarnEnabled()) {
			response.remove(B2ResponseProperties.KEY_FILE_ID);
			response.remove(B2ResponseProperties.KEY_FILE_NAME);
			response.remove(B2ResponseProperties.KEY_ACCOUNT_ID);
			response.remove(B2ResponseProperties.KEY_BUCKET_ID);
			response.remove(B2ResponseProperties.KEY_CONTENT_LENGTH);
			response.remove(B2ResponseProperties.KEY_CONTENT_SHA1);
			response.remove(B2ResponseProperties.KEY_CONTENT_TYPE);

			warnOnMissedKeys(LOGGER, response);
		}
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public String getAccountId() { return this.accountId; }
	public String getBucketId() { return this.bucketId; }
	public long getContentLength() { return this.contentLength; }
	public String getContentSha1() { return this.contentSha1; }
	public String getContentType() { return this.contentType; }
	public Map<String, String> getFileInfo() { return this.fileInfo; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append(", bucketId='").append(bucketId).append('\'');
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", contentType='").append(contentType).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append('}');
		return sb.toString();
	}
}
