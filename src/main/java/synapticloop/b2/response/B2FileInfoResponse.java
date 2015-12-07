package synapticloop.b2.response;

import org.json.JSONObject;

public class B2FileInfoResponse extends BaseB2Response {
	private String fileId = null;
	private String fileName = null;
	private String action = null;
	private int size = -1;
	private long uploadTimestamp = -1;

	public B2FileInfoResponse(JSONObject jsonObject) {
		this.fileId = jsonObject.optString(KEY_FILE_ID);
		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		this.action = jsonObject.optString(KEY_ACTION);
		this.size = jsonObject.optInt(KEY_SIZE);
		this.uploadTimestamp = jsonObject.optLong(KEY_UPLOAD_TIMESTAMP);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public String getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }
}
