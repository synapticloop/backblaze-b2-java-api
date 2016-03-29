package synapticloop.b2.request;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2ListFilesResponse;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class B2ListUnfinishedLargeFilesRequestTest {

	@Test
	public void getResponse() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();

		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		final B2ListFilesResponse b2ListFilesResponse = new B2ListUnfinishedLargeFilesRequest(HttpClients.createDefault(),
				b2AuthorizeAccountResponse, privateBucketId).getResponse();
		assertNull(b2ListFilesResponse.getNextFileId());
		assertTrue(b2ListFilesResponse.getFiles().isEmpty());

		B2TestHelper.deleteBucket(privateBucketId);
	}
}