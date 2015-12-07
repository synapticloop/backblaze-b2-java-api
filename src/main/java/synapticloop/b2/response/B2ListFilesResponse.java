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

	public B2ListFilesResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.nextFileName = jsonObject.optString("nextFileName");
		this.nextFileId = jsonObject.optString("nextFileId");

		JSONArray filesArray = jsonObject.optJSONArray("files");
		
		// now go through the filesArray
		
		for(int i = 0; i < filesArray.length(); i ++) {
			files.add(new B2FileInfoResponse(filesArray.optJSONObject(i)));
		}
	}

	public String getNextFileName() { return this.nextFileName; }
	public String getNextFileId() { return this.nextFileId; }
	public List<B2FileInfoResponse> getFiles() { return this.files; }

}
