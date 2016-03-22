package synapticloop.b2.request;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import synapticloop.b2.BucketType;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2StartLargeFileResponse;

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.*;

public class B2StartLargeFileRequestTest {

	@Test
	public void getResponse() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();

		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		B2StartLargeFileResponse b2StartLargeFileResponse = new B2StartLargeFileRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse,
				privateBucketId, UUID.randomUUID().toString(), null, Collections.emptyMap()).getResponse();
		assertNotNull(b2StartLargeFileResponse.getFileId());

		final B2FileResponse b2FileResponse = new B2CancelLargeFileRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse,
				b2StartLargeFileResponse.getFileId()).getResponse();
		assertEquals(b2StartLargeFileResponse.getFileId(), b2FileResponse.getFileId());

		B2TestHelper.deleteBucket(privateBucketId);
	}
}