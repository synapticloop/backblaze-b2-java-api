package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2Exception;

public class B2BucketResponse extends BaseB2Response {
	private final String bucketId;
	private final String accountId;
	private final String bucketName;
	private final String bucketType;

	/**
	 * Instantiate an bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param json The response (in JSON format)
	 * 
	 * @throws B2Exception if there was an error parsing the response
	 */

	public B2BucketResponse(String json) throws B2Exception {
		super(json);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketName = response.optString(B2ResponseProperties.KEY_BUCKET_NAME, null);
		this.bucketType = response.optString(B2ResponseProperties.KEY_BUCKET_TYPE, null);
	}

	public B2BucketResponse(final JSONObject response) throws B2Exception {
		super(response);

		this.bucketId = response.optString(B2ResponseProperties.KEY_BUCKET_ID, null);
		this.accountId = response.optString(B2ResponseProperties.KEY_ACCOUNT_ID, null);
		this.bucketName = response.optString(B2ResponseProperties.KEY_BUCKET_NAME, null);
		this.bucketType = response.optString(B2ResponseProperties.KEY_BUCKET_TYPE, null);
	}

	/**
	 * Get the bucket id
	 * 
	 * @return the id of the bucket
	 */
	public String getBucketId() { return this.bucketId; }
	
	/**
	 * Get the account id
	 * 
	 * @return the id of the account
	 */
	public String getAccountId() { return this.accountId; }

	/**
	 * Get the name of the bucket
	 * 
	 * @return the name of the bucket
	 */
	public String getBucketName() { return this.bucketName; }

	/**
	 * Get the type of the bucket, on of allPrivate or allPublic
	 * 
	 * @return The bucket type
	 */
	public BucketType getBucketType() { return BucketType.valueOf(this.bucketType); }

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("B2BucketResponse{");
		sb.append("bucketId='").append(bucketId).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append(", bucketName='").append(bucketName).append('\'');
		sb.append(", bucketType='").append(bucketType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
