package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.BucketType;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

public class B2UpdateBucketRequestTest {

	@Test
	public void testUpdateBucket() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();

		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		B2BucketResponse privateToPublicResponse = new B2UpdateBucketRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, privateBucketId, BucketType.allPublic).getResponse();
		assertEquals(BucketType.allPublic, privateToPublicResponse.getBucketType());

		B2TestHelper.deleteBucket(privateBucketId);

		B2BucketResponse createRandomPublicBucket = B2TestHelper.createRandomPublicBucket();

		String publicBucketId = createRandomPublicBucket.getBucketId();
		B2BucketResponse publicToPrivateResponse = new B2UpdateBucketRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, publicBucketId, BucketType.allPrivate).getResponse();
		assertEquals(BucketType.allPrivate, publicToPrivateResponse.getBucketType());
		B2TestHelper.deleteBucket(publicBucketId);
	}

}
