package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;

public class B2HeadFileRequestTest {

	@Test
	public void testHeadFileById() throws Exception {
		final B2BucketResponse randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();

		Map<String, String> fileInfo = new HashMap<String, String>();
		fileInfo.put("hello", "world");

		final B2FileResponse b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId(), fileInfo);

		B2DownloadFileResponse b2DownloadFileResponse = new B2HeadFileByIdRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), b2FileResponse.getFileId()).getResponse();
		assertEquals("world", b2DownloadFileResponse.getFileInfo().get("hello"));

		assertEquals(0, b2DownloadFileResponse.getContent().available());

		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

}
