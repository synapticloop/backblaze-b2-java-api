package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.UUID;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2FinishLargeFileResponse;
import synapticloop.b2.response.B2GetUploadPartUrlResponse;
import synapticloop.b2.response.B2ListPartsResponse;
import synapticloop.b2.response.B2StartLargeFileResponse;
import synapticloop.b2.response.B2UploadPartResponse;

public class B2UploadPartRequestTest {

	// this is expected until the large file support goes live
	@Test(expected=B2ApiException.class)
	public void getResponse() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();

		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();

		String privateBucketId = privateBucket.getBucketId();
		final CloseableHttpClient client = HttpClients.createDefault();

		B2StartLargeFileResponse b2StartLargeFileResponse = new B2StartLargeFileRequest(client, b2AuthorizeAccountResponse,
				privateBucketId, UUID.randomUUID().toString(), null, Collections.<String, String>emptyMap()).getResponse();
		assertNotNull(b2StartLargeFileResponse.getFileId());

		final B2GetUploadPartUrlResponse b2GetUploadPartUrlResponse1 = new B2GetUploadPartUrlRequest(client,
				b2AuthorizeAccountResponse, b2StartLargeFileResponse.getFileId()).getResponse();
		final B2UploadPartResponse b2UploadPartResponse1 = new B2UploadPartRequest(client, b2AuthorizeAccountResponse, b2GetUploadPartUrlResponse1, 1,
				new StringEntity(B2TestHelper.DUMMY_FILE_CONTENT), "430ce34d020724ed75a196dfc2ad67c77772d169").getResponse();
		assertEquals("430ce34d020724ed75a196dfc2ad67c77772d169", b2UploadPartResponse1.getContentSha1());

		final B2GetUploadPartUrlResponse b2GetUploadPartUrlResponse2 = new B2GetUploadPartUrlRequest(client,
				b2AuthorizeAccountResponse, b2StartLargeFileResponse.getFileId()).getResponse();
		final B2UploadPartResponse b2UploadPartResponse2 = new B2UploadPartRequest(client, b2AuthorizeAccountResponse, b2GetUploadPartUrlResponse2, 2,
				new StringEntity(B2TestHelper.DUMMY_FILE_CONTENT), "430ce34d020724ed75a196dfc2ad67c77772d169").getResponse();
		assertEquals("430ce34d020724ed75a196dfc2ad67c77772d169", b2UploadPartResponse2.getContentSha1());

		final B2ListPartsResponse b2ListPartsResponse = new B2ListPartsRequest(client, b2AuthorizeAccountResponse,
				b2StartLargeFileResponse.getFileId()).getResponse();
		assertEquals(2, b2ListPartsResponse.getFiles().size());

		try {
			final B2FinishLargeFileResponse b2FinishLargeFileResponse = new B2FinishLargeFileRequest(client, b2AuthorizeAccountResponse,
					b2StartLargeFileResponse.getFileId(), new String[]{"430ce34d020724ed75a196dfc2ad67c77772d169", "430ce34d020724ed75a196dfc2ad67c77772d169"}).getResponse();
			fail();
		} catch (B2ApiException e) {
			assertEquals(400, e.getStatus());
			assertEquals("part number 1 is smaller than 100000000 bytes", e.getMessage());
		}

		final B2FileResponse b2FileResponse = new B2CancelLargeFileRequest(client, b2AuthorizeAccountResponse,
				b2StartLargeFileResponse.getFileId()).getResponse();
		assertEquals(b2StartLargeFileResponse.getFileId(), b2FileResponse.getFileId());

		B2TestHelper.deleteBucket(privateBucketId);

	}
}