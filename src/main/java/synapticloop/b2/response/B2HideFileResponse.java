package synapticloop.b2.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2ApiException;

public class B2HideFileResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2HideFileResponse.class);

	private String fileId = null;
	private String fileName = null;
	private Action action = null;
	private long size = -1;
	private long uploadTimestamp = -1;

	public B2HideFileResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.fileId = jsonObject.optString(KEY_FILE_ID);
		jsonObject.remove(KEY_FILE_ID);

		this.fileName = jsonObject.optString(KEY_FILE_NAME);
		jsonObject.remove(KEY_FILE_NAME);

		// TODO - this should always be 'hide'
		String actionTemp = jsonObject.optString(KEY_ACTION);
		if(null != actionTemp && actionTemp.compareTo(Action.HIDE.toString()) == 0) {
			this.action = Action.HIDE;
		} else {
			this.action = Action.UPLOAD;
		}
		jsonObject.remove(KEY_ACTION);

		this.size = jsonObject.optLong(KEY_SIZE);
		jsonObject.remove(KEY_SIZE);

		this.uploadTimestamp = jsonObject.optLong(KEY_UPLOAD_TIMESTAMP);
		jsonObject.remove(KEY_UPLOAD_TIMESTAMP);

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	/**
	 * Get the unique identifier for this version of this file, this will return
	 * null if the id of the file could not be parsed from the response.
	 * 
	 * @return The unique identifier for this version of this file.
	 */
	public String getFileId() { return this.fileId; }

	/**
	 * Get the name of this file, this will return null if the name of the file
	 * could not be parsed from the response.
	 * 
	 * @return the name of this file
	 */
	public String getFileName() { return this.fileName; }

	/**
	 * Either "upload" or "hide". "upload" means a file that was uploaded to B2 
	 * Cloud Storage. "hide" means a file version marking the file as hidden, 
	 * so that it will not show up in b2_list_file_names.
	 * 
	 * @return the action for the file
	 */
	public Action getAction() { return this.action; }

	/**
	 * Get the number of bytes in the file, this will return -1 if the response
	 * could not be parsed
	 * 
	 * @return the number of bytes in the file
	 */
	public long getSize() { return this.size; }

	/**
	 * Get the timestamp that the hide took effect.  This will return -1 if the
	 * response could not be parsed.
	 * 
	 * @return the timestamp that the hide took effect.
	 */
	public long getUploadTimestamp() { return this.uploadTimestamp; }
}
