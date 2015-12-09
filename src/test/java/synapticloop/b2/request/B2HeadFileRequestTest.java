package synapticloop.b2.request;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;

public class B2HeadFileRequestTest {
	private B2BucketResponse randomPrivateBucket = null;
	private B2FileResponse b2FileResponse = null;
	private Map<String, String> fileInfo = new HashMap<String, String>();

	@Test
	public void testHeadFileById() throws B2ApiException, IOException {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();

		fileInfo.put("hello", "world");

		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId(), fileInfo);

		B2DownloadFileResponse b2DownloadFileResponse = new B2HeadFileByIdRequest(B2TestHelper.getB2AuthorizeAccountResponse(), b2FileResponse.getFileId()).getResponse();
		assertEquals("world", b2DownloadFileResponse.getFileInfo().get("hello"));

		assertNull(b2DownloadFileResponse.getContent());
	}

}
