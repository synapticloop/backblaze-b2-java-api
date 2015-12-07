package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2DeleteFileVersionResponse extends BaseB2Response {
	private String fileId = null;
	private String fileName = null;

	public B2DeleteFileVersionResponse(String response) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(response);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		this.fileName = jsonObject.optString(KEY_FILE_NAME);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
}
