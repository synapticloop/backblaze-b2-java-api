package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;


public class B2ListBucketsRequestTest {

	private B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;

	@Before
	public void setup() throws B2ApiException {
		b2AuthorizeAccountResponse = B2Helper.getB2AuthorizeAccountResponse();
	}

	@Test
	public void testListBuckets() throws B2ApiException {
		// first we want to create a bucker
		B2BucketResponse createRandomPrivateBucket = B2Helper.createRandomPrivateBucket();
		String bucketName = createRandomPrivateBucket.getBucketName();
		String bucketId = createRandomPrivateBucket.getBucketId();

		B2ListBucketsRequest b2ListBucketsRequest = new B2ListBucketsRequest(b2AuthorizeAccountResponse);
		List<B2BucketResponse> responses = b2ListBucketsRequest.getResponse();
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
		B2Helper.deleteBucket(bucketId);
	}

}
