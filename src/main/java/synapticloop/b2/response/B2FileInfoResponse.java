package synapticloop.b2.response;

import org.json.JSONObject;

public class B2FileInfoResponse {
	private String fileId = null;
	private String fileName = null;
	private String action = null;
	private int size = -1;
	private long uploadTimestamp = -1;

	public B2FileInfoResponse(JSONObject jsonObject) {
		this.fileId = jsonObject.optString("fileId");
		this.fileName = jsonObject.optString("fileName");
		this.action = jsonObject.optString("action");
		this.size = jsonObject.optInt("size");
		this.uploadTimestamp = jsonObject.optLong("uploadTimestamp");
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public String getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }
}
