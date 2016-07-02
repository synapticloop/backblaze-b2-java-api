package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;

public class B2GetUploadUrlRequestTest {

	@Test
	public void testGetUploadUrl() throws Exception {
		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		B2GetUploadUrlResponse response = new B2GetUploadUrlRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), privateBucket.getBucketId()).getResponse();

		assertNotNull(response.getAuthorizationToken());
		assertEquals(privateBucket.getBucketId(), response.getBucketId());
		assertNotNull(response.getBucketId());
		assertNotNull(response.getUploadUrl());

		B2TestHelper.deleteBucket(privateBucket.getBucketId());
	}

}
