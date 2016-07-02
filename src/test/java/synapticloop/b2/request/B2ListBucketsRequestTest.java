package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;


public class B2ListBucketsRequestTest {

	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;

	@Before
	public void setup() throws Exception {
		b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
	}

	@Test
	public void testListBuckets() throws Exception {
		// first we want to create a bucker
		B2BucketResponse createRandomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		String bucketName = createRandomPrivateBucket.getBucketName();
		String bucketId = createRandomPrivateBucket.getBucketId();

		B2ListBucketsRequest b2ListBucketsRequest = new B2ListBucketsRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse);
		List<B2BucketResponse> responses = b2ListBucketsRequest.getResponse().getBuckets();
		// this may actually not be the greatest test as there may already be more than one bucket...
		assertTrue(responses.size() > 0);

		// so we test to ensure that we have the named bucket

		boolean hasFoundBucket = false;
		for (B2BucketResponse b2BucketResponse : responses) {
			if(bucketName.equals(b2BucketResponse.getBucketName())) {
				hasFoundBucket = true;
			}
		}

		assertTrue(hasFoundBucket);

		// in any case - we want to delete it
		B2TestHelper.deleteBucket(bucketId);
	}

}
