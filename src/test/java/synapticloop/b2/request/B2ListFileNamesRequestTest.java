package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2ListFilesResponse;

public class B2ListFileNamesRequestTest {

	@Test
	public void testListEmptyBucket() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		B2BucketResponse b2BucketResponse = B2TestHelper.createRandomPrivateBucket();

		B2ListFilesResponse b2ListFilesResponse = new B2ListFileNamesRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, b2BucketResponse.getBucketId()).getResponse();

		assertNull(b2ListFilesResponse.getNextFileId());
		assertNull(b2ListFilesResponse.getNextFileName());

		B2TestHelper.deleteBucket(b2BucketResponse.getBucketId());
	}
}