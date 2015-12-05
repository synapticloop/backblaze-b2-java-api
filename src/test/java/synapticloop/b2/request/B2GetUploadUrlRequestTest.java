package synapticloop.b2.request;
import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;


public class B2GetUploadUrlRequestTest {

	@Test
	public void testGetUploadUrl() throws B2ApiException {
		B2BucketResponse privateBucket = B2Helper.createRandomPrivateBucket();

		B2GetUploadUrlResponse response = new B2GetUploadUrlRequest(B2Helper.getB2AuthorizeAccountResponse(), privateBucket.getBucketId()).getResponse();

		assertNotNull(response.getAuthorizationToken());
		assertEquals(privateBucket.getBucketId(), response.getBucketId());
		assertNotNull(response.getBucketId());
		assertNotNull(response.getUploadUrl());

		B2Helper.deleteBucket(privateBucket.getBucketId());
	}

}
