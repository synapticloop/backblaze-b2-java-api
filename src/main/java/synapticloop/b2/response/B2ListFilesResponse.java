package synapticloop.b2.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2ListFilesResponse extends BaseB2Response {
	private List<B2FileInfoResponse> files = new ArrayList<B2FileInfoResponse>();
	private String nextFileName = null;
	private String nextFileId = null;

	/**
	 * Instantiate a list files response with the JSON response as a 
	 * string from the API call.  This response is then parsed into the 
	 * relevant fields.
	 * 
	 * @param response the response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */
	public B2ListFilesResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.nextFileName = jsonObject.optString(KEY_NEXT_FILE_NAME);
		this.nextFileId = jsonObject.optString(KEY_NEXT_FILE_ID);

		JSONArray filesArray = jsonObject.optJSONArray(KEY_FILES);

		// now go through the filesArray
		for(int i = 0; i < filesArray.length(); i ++) {
			files.add(new B2FileInfoResponse(filesArray.optJSONObject(i)));
		}
	}

	/**
	 * get the next file name that is the next result to be returned after this 
	 * result set - or null if there are no more files
	 * 
	 * @return the next file name to start the next iteration (or null if no next file)
	 */
	public String getNextFileName() { return this.nextFileName; }

	/**
	 * get the next file id that is the next result to be returned after this 
	 * result set - or null if there are no more files
	 * 
	 * @return the next file id to start the next iteration (or null if no next file id)
	 */
	public String getNextFileId() { return this.nextFileId; }

	/**
	 * Return the list of files include file info
	 * 
	 * @return the list of files for this request
	 */
	public List<B2FileInfoResponse> getFiles() { return this.files; }
}
