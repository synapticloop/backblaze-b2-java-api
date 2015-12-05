package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

public class B2UpdateBucketRequestTest {
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;

	@Before
	public void setup() throws B2ApiException {
		b2AuthorizeAccountResponse = B2Helper.getB2AuthorizeAccountResponse();
	}

	@Test
	public void testUpdateBucket() throws B2ApiException {
		B2BucketResponse privateBucket = B2Helper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		B2BucketResponse privateToPublicResponse = new B2UpdateBucketRequest(b2AuthorizeAccountResponse, privateBucketId, BucketType.ALL_PUBLIC).getResponse();
		assertEquals(BucketType.ALL_PUBLIC.toString(), privateToPublicResponse.getBucketType());

		B2Helper.deleteBucket(privateBucketId);

		B2BucketResponse createRandomPublicBucket = B2Helper.createRandomPublicBucket();

		String publicBucketId = createRandomPublicBucket.getBucketId();
		B2BucketResponse publicToPrivateResponse = new B2UpdateBucketRequest(b2AuthorizeAccountResponse, publicBucketId, BucketType.ALL_PRIVATE).getResponse();
		assertEquals(BucketType.ALL_PRIVATE.toString(), publicToPrivateResponse.getBucketType());
		B2Helper.deleteBucket(publicBucketId);
	}

}
