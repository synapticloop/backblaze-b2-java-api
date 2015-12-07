package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2HideFileResponse extends BaseB2Response {

	private String fileId = null;
	private String fileName = null;
	private String action = null;
	private long size = -1;

	public B2HideFileResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		this.action = jsonObject.optString(KEY_ACTION);
		this.size = jsonObject.optLong(KEY_SIZE);
	}

	public String getFileId() {
		return this.fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getAction() {
		return this.action;
	}

	public long getSize() {
		return this.size;
	}
}
