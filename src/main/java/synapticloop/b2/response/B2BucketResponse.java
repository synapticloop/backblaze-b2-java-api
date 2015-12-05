package synapticloop.b2.response;

import java.io.InputStream;

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

	public B2BucketResponse(InputStream inputStream) throws B2ApiException {
		this(getParsedResponse(inputStream));
	}

	public B2BucketResponse(JSONObject jsonObject) {
		this.bucketId = jsonObject.optString("bucketId");
		this.accountId = jsonObject.optString("accountId");
		this.bucketName = jsonObject.optString("bucketName");
		this.bucketType = jsonObject.optString("bucketType");
	}

	public String getBucketId() { return this.bucketId; }
	public String getAccountId() { return this.accountId; }
	public String getBucketName() { return this.bucketName; }
	public String getBucketType() { return this.bucketType; }

}
