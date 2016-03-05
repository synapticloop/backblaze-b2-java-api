package synapticloop.b2.response;

import synapticloop.b2.exception.B2Exception;

public class B2DeleteFileVersionResponse extends BaseB2Response {
	private final String fileId;
	private final String fileName;

	public B2DeleteFileVersionResponse(String json) throws B2Exception {
		super(json);

		this.fileId = response.optString(B2ResponseProperties.KEY_FILE_ID, null);
		this.fileName = response.optString(B2ResponseProperties.KEY_FILE_NAME, null);
	}

	public String getFileId() { return this.fileId; }
	public String getFileName() { return this.fileName; }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2DeleteFileVersionResponse{");
		sb.append("fileId='").append(fileId).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
