package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2Exception;

public class B2FileInfoResponse extends BaseB2Response {
	private final String fileId;
	private final String fileName;
	private final Action action;
	private final Integer size;
	private final Long uploadTimestamp;

    public B2FileInfoResponse(final JSONObject response) throws B2Exception {
        super(response);

        this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME);

		String action = response.optString(B2ResponseProperties.KEY_ACTION);
        if(null != action) {
            this.action = Action.valueOf(action);
        }
        else {
            // Default
            this.action = Action.upload;
        }
		this.size = response.optInt(B2ResponseProperties.KEY_SIZE);
		this.uploadTimestamp = response.optLong(B2ResponseProperties.KEY_UPLOAD_TIMESTAMP);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }
	public Action getAction() { return this.action; }
	public int getSize() { return this.size; }
	public long getUploadTimestamp() { return this.uploadTimestamp; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2FileInfoResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", action=").append(action);
		sb.append(", size=").append(size);
		sb.append(", uploadTimestamp=").append(uploadTimestamp);
		sb.append('}');
		return sb.toString();
	}
}
