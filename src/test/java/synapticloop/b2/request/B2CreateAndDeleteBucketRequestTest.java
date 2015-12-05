package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

public class B2CreateAndDeleteBucketRequestTest {
	private B2CreateBucketRequest b2CreateBucketRequest = null;
	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private String bucketName = null;

	@Before
	public void setup() throws B2ApiException {
		b2AuthorizeAccountResponse = B2Helper.getB2AuthorizeAccountResponse();
		bucketName = UUID.randomUUID().toString();
	}

	@Test
	public void testBucketCreationAndDeletion() throws B2ApiException {
		
		b2CreateBucketRequest = new B2CreateBucketRequest(b2AuthorizeAccountResponse, bucketName, BucketType.ALL_PRIVATE);
		B2BucketResponse b2BucketResponse = b2CreateBucketRequest.getResponse();
		assertEquals(b2AuthorizeAccountResponse.getAccountId(), b2BucketResponse.getAccountId());
		String bucketId = b2BucketResponse.getBucketId();
		assertNotNull(bucketId);
		assertEquals(bucketName, b2BucketResponse.getBucketName());
		assertEquals(BucketType.ALL_PRIVATE.toString(), b2BucketResponse.getBucketType());

		B2DeleteBucketRequest b2DeleteBucketRequest = new B2DeleteBucketRequest(b2AuthorizeAccountResponse, bucketId);
		B2BucketResponse response = b2DeleteBucketRequest.getResponse();
		assertNotNull(response.getBucketId());
		assertEquals(bucketId, response.getBucketId());
		assertEquals(bucketName, b2BucketResponse.getBucketName());
		assertEquals(BucketType.ALL_PRIVATE.toString(), b2BucketResponse.getBucketType());
	}
}
