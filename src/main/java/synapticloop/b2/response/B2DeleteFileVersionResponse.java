package synapticloop.b2.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2DeleteFileVersionResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2DeleteFileVersionResponse.class);

	private String fileId = null;
	private String fileName = null;

	public B2DeleteFileVersionResponse(String response) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(response);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		jsonObject.remove(KEY_FILE_ID);

		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		jsonObject.remove(KEY_FILE_NAME);

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
}
