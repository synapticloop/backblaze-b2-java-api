package synapticloop.b2.response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import synapticloop.b2.exception.B2Exception;

public class B2FileResponse extends BaseB2Response {
	private final String fileId;
	private final String fileName;
	private final String accountId;
	private final String bucketId;
	private final Long contentLength;
	private final String contentSha1;
	private final String contentType;
	private final Map<String, Object> fileInfo;

	public B2FileResponse(String json) throws B2Exception {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID);
		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID);
		this.contentLength = response.optLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = response.optString(B2ResponseProperties.KEY_CONTENT_SHA1);
		this.contentType = response.optString(B2ResponseProperties.KEY_CONTENT_TYPE);
		this.fileInfo = new HashMap<>();
		JSONObject fileInfoObject = response.optJSONObject(B2ResponseProperties.KEY_FILE_INFO);
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
