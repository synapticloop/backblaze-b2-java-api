package synapticloop.b2.response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2ApiException;

public class B2GetUploadUrlResponse extends BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(B2GetUploadUrlResponse.class);

	private String bucketId = null;
	private String uploadUrl = null;
	private String authorizationToken = null;

	public B2GetUploadUrlResponse(String string) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(string);

		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		jsonObject.remove(KEY_BUCKET_ID);

		this.uploadUrl = jsonObject.optString(KEY_UPLOAD_URL);
		jsonObject.remove(KEY_UPLOAD_URL);

		this.authorizationToken = jsonObject.optString(KEY_AUTHORIZATION_TOKEN);
		jsonObject.remove(KEY_AUTHORIZATION_TOKEN);

		warnOnMissedKeys(LOGGER, jsonObject);
	}

	public String getBucketId() { return this.bucketId; }
	public String getUploadUrl() { return this.uploadUrl; }
	public String getAuthorizationToken() { return this.authorizationToken; }

}
