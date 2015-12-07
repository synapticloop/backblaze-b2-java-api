package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2BucketResponse extends BaseB2Response {
	private String bucketId = null;
	private String accountId = null;
	private String bucketName = null;
	private String bucketType = null;

	public B2BucketResponse(String data) throws B2ApiException {
		this(getParsedResponse(data));
	}

	public B2BucketResponse(JSONObject jsonObject) {
		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		this.bucketName = jsonObject.optString(KEY_BUCKET_NAME);
		this.bucketType = jsonObject.optString(KEY_BUCKET_TYPE);
	}

	public String getBucketId() { return this.bucketId; }
	public String getAccountId() { return this.accountId; }
	public String getBucketName() { return this.bucketName; }
	public String getBucketType() { return this.bucketType; }

}
