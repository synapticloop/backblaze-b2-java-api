package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2GetUploadUrlResponse extends BaseB2Response {
	private String bucketId = null;
	private String uploadUrl = null;
	private String authorizationToken = null;

	public B2GetUploadUrlResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		this.uploadUrl = jsonObject.optString(KEY_UPLOAD_URL);
		this.authorizationToken = jsonObject.optString(KEY_AUTHORIZATION_TOKEN);
	}

	public String getBucketId() { return this.bucketId; }
	public String getUploadUrl() { return this.uploadUrl; }
	public String getAuthorizationToken() { return this.authorizationToken; }

}
