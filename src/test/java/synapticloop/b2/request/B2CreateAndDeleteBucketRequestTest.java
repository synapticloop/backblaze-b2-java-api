package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;

import synapticloop.b2.BucketType;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;

public class B2CreateAndDeleteBucketRequestTest {
	private B2CreateBucketRequest b2CreateBucketRequest = null;
	private static B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private static String bucketName = null;

	@BeforeClass
	public static void setupBeforClass() throws Exception {
		b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		bucketName = UUID.randomUUID().toString();
	}

	@Test
	public void testBucketCreationAndDeletion() throws Exception {

		b2CreateBucketRequest = new B2CreateBucketRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketName, BucketType.allPrivate);
		B2BucketResponse b2BucketResponse = b2CreateBucketRequest.getResponse();
		assertEquals(b2AuthorizeAccountResponse.getAccountId(), b2BucketResponse.getAccountId());
		String bucketId = b2BucketResponse.getBucketId();
		assertNotNull(bucketId);
		assertEquals(bucketName, b2BucketResponse.getBucketName());
		assertEquals(BucketType.allPrivate, b2BucketResponse.getBucketType());

		B2DeleteBucketRequest b2DeleteBucketRequest = new B2DeleteBucketRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketId);
		B2BucketResponse response = b2DeleteBucketRequest.getResponse();

		assertNotNull(response.getBucketId());
		assertEquals(bucketId, response.getBucketId());
		assertEquals(bucketName, b2BucketResponse.getBucketName());
		assertEquals(BucketType.allPrivate, b2BucketResponse.getBucketType());
	}
}
