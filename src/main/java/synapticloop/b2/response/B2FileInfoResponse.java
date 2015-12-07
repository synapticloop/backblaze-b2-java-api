package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.ActionType;

public class B2FileInfoResponse extends BaseB2Response {
	private String fileId = null;
	private String fileName = null;
	private ActionType action = null;
	private int size = -1;
	private long uploadTimestamp = -1;

	public B2FileInfoResponse(JSONObject jsonObject) {
		this.fileId = jsonObject.optString(KEY_FILE_ID);
		this.fileName = jsonObject.optString(KEY_FILE_NAME);

		String actionTemp = jsonObject.optString(KEY_ACTION);
		if(null != actionTemp && actionTemp.compareTo("hide") == 0) {
			this.action = ActionType.HIDE;
		} else {
			this.action = ActionType.UPLOAD;
		}

		this.size = jsonObject.optInt(KEY_SIZE);
		this.uploadTimestamp = jsonObject.optLong(KEY_UPLOAD_TIMESTAMP);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public ActionType getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }
}
