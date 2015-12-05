package synapticloop.b2.response;

import java.io.InputStream;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2GetUploadUrlResponse extends BaseB2Response {
	private String bucketId = null;
	private String uploadUrl = null;
	private String authorizationToken = null;

	public B2GetUploadUrlResponse(InputStream inputStream) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(inputStream);

		this.bucketId = jsonObject.optString("bucketId");
		this.uploadUrl = jsonObject.optString("uploadUrl");
		this.authorizationToken = jsonObject.optString("authorizationToken");
	}

	public String getBucketId() { return this.bucketId; }
	public String getUploadUrl() { return this.uploadUrl; }
	public String getAuthorizationToken() { return this.authorizationToken; }

}
