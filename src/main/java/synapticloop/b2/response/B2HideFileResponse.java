package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

public class B2HideFileResponse extends BaseB2Response {

	private String fileId = null;
	private String fileName = null;
	private Action action = null;
	private long size = -1;

	public B2HideFileResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		String actionTemp = jsonObject.optString(KEY_ACTION);
		if(null != actionTemp && actionTemp.compareTo(Action.HIDE.toString()) == 0) {
			this.action = Action.HIDE;
		} else {
			this.action = Action.UPLOAD;
		}
		this.size = jsonObject.optLong(KEY_SIZE);
	}

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public Action getAction() { return this.action; }

	public long getSize() { return this.size; }
}
