package synapticloop.b2.response;

import synapticloop.b2.Action;
import synapticloop.b2.exception.B2Exception;

public class B2HideFileResponse extends BaseB2Response {
	private final String fileId;
	private final String fileName;
	private final Action action;
	private final Long size;

	public B2HideFileResponse(String json) throws B2Exception {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME);
		String actionTemp = response.optString(B2ResponseProperties.KEY_ACTION);
		if(null != actionTemp && actionTemp.compareTo(Action.hide.toString()) == 0) {
			this.action = Action.hide;
		} else {
			this.action = Action.upload;
		}
		this.size = response.optLong(B2ResponseProperties.KEY_SIZE);
	}

	public String getFileId() { return this.fileId; }

	public String getFileName() { return this.fileName; }

	public Action getAction() { return this.action; }

	public long getSize() { return this.size; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2HideFileResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", action=").append(action);
		sb.append(", size=").append(size);
		sb.append('}');
		return sb.toString();
	}
}
