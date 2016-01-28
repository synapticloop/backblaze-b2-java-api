package synapticloop.b2.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.Action;

public class B2FileInfoResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2FileInfoResponse.class);

	private String fileId = null;
	private String fileName = null;
	private Action action = null;
	private int size = -1;
	private long uploadTimestamp = -1;

	public B2FileInfoResponse(JSONObject jsonObject) {
		this.fileId = jsonObject.optString(KEY_FILE_ID);
		jsonObject.remove(KEY_FILE_ID);

		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		jsonObject.remove(KEY_FILE_NAME);

		String actionTemp = jsonObject.optString(KEY_ACTION);
		if(null != actionTemp && actionTemp.compareTo("hide") == 0) {
			this.action = Action.HIDE;
		} else {
			this.action = Action.UPLOAD;
		}
		jsonObject.remove(KEY_ACTION);

		this.size = jsonObject.optInt(KEY_SIZE);
		jsonObject.remove(KEY_SIZE);

		this.uploadTimestamp = jsonObject.optLong(KEY_UPLOAD_TIMESTAMP);
		jsonObject.remove(KEY_UPLOAD_TIMESTAMP);

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public Action getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }
}
