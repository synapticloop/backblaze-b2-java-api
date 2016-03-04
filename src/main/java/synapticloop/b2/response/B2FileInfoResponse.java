package synapticloop.b2.response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2Exception;

public class B2FileInfoResponse extends BaseB2Response {
	private final String fileId;
	private final String fileName;
	private final Long contentLength;
	private final String contentSha1;
	private final Map<String, Object> fileInfo;
	private final Action action;
	private final Integer size;
	private final Long uploadTimestamp;

    public B2FileInfoResponse(final JSONObject response) throws B2Exception {
        super(response);

        this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);
		this.contentLength = response.optLong(B2ResponseProperties.KEY_CONTENT_LENGTH);
		this.contentSha1 = response.optString(B2ResponseProperties.KEY_CONTENT_SHA1, null);
		this.fileInfo = new HashMap<>();
		JSONObject fileInfoObject = response.optJSONObject(B2ResponseProperties.KEY_FILE_INFO);
		if(null != fileInfoObject) {
			Iterator keys = fileInfoObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				fileInfo.put(key, fileInfoObject.opt(key));
			}
		}

		String action = response.optString(B2ResponseProperties.KEY_ACTION, null);
        if(null != action) {
            this.action = Action.valueOf(action);
        }
        else {
            // Default
            this.action = Action.upload;
        }
		this.size = response.optInt(B2ResponseProperties.KEY_SIZE);
		this.uploadTimestamp = response.optLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public long getContentLength() { return this.contentLength; }
	public String getContentSha1() { return this.contentSha1; }
	public Map<String, Object> getFileInfo() { return this.fileInfo; }
	public Action getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FileInfoResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", contentLength=").append(contentLength);
		sb.append(", contentSha1='").append(contentSha1).append('\'');
		sb.append(", fileInfo=").append(fileInfo);
		sb.append(", action=").append(action);
		sb.append(", size=").append(size);
		sb.append(", uploadTimestamp=").append(uploadTimestamp);
		sb.append('}');
		return sb.toString();
	}
}
