package synapticloop.b2.request;
import static org.junit.Assert.*;

import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2HideFileResponse;
import synapticloop.b2.response.B2ListFilesResponse;


public class B2HideFileRequestTest {

	private B2BucketResponse randomPrivateBucket = null;
	private B2FileResponse b2FileResponseWithPath = null;
	private B2HideFileResponse b2HideFileResponse = null;

	@Before
	public void setup() {
	}

	@Test
	public void testHideFileWithPath() throws Exception {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		String bucketId = randomPrivateBucket.getBucketId();
		b2FileResponseWithPath = B2TestHelper.uploadTemporaryFileToBucket(bucketId);

		B2ListFilesResponse b2ListFilesResponse = new B2ListFileNamesRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), bucketId).getResponse();

		assertEquals(1, b2ListFilesResponse.getFiles().size());

		b2HideFileResponse  = new B2HideFileRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), 
				bucketId,
				b2FileResponseWithPath.getFileName()).getResponse();

		assertEquals("hide", b2HideFileResponse.getAction().toString());

		// we now have two versions...
		b2ListFilesResponse = new B2ListFileNamesRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), 
				bucketId).getResponse();

		assertEquals(2, b2ListFilesResponse.getFiles().size());

	}

	@After
	public void tearDown() throws Exception {
		B2TestHelper.deleteFile(b2HideFileResponse.getFileName(), b2HideFileResponse.getFileId());
		B2TestHelper.deleteFile(b2FileResponseWithPath.getFileName(), b2FileResponseWithPath.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}
}
