package synapticloop.b2.response;

import org.json.JSONObject;

import synapticloop.b2.BucketType;
import synapticloop.b2.exception.B2ApiException;

public class B2BucketResponse extends BaseB2Response {
	private String bucketId = null;
	private String accountId = null;
	private String bucketName = null;
	private String bucketType = null;

	/**
	 * Instantiate an bucket response with the JSON response as a string from 
	 * the API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param response the response (in JSON format)
	 * 
	 * @throws B2ApiException if there was an error parsing the response
	 */

	public B2BucketResponse(String data) throws B2ApiException {
		this(getParsedResponse(data));
	}

	/**
	 * Instantiate an bucket response with a pre-parsed JSON response from the 
	 * API call.  This response is then parsed into the relevant fields.
	 * 
	 * @param jsonObject The pre-parsed json object
	 */
	public B2BucketResponse(JSONObject jsonObject) {
		this.bucketId = jsonObject.optString(KEY_BUCKET_ID);
		this.accountId = jsonObject.optString(KEY_ACCOUNT_ID);
		this.bucketName = jsonObject.optString(KEY_BUCKET_NAME);
		this.bucketType = jsonObject.optString(KEY_BUCKET_TYPE);
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

}
