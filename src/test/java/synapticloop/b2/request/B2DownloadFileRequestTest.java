package synapticloop.b2.request;
import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;

public class B2DownloadFileRequestTest {
	private B2BucketResponse randomPrivateBucket = null;
	private B2FileResponse b2FileResponse = null;

//	@Test
	public void testDownloadFileBy() throws B2ApiException, IOException {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId());

		B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByNameRequest(B2TestHelper.getB2AuthorizeAccountResponse(), randomPrivateBucket.getBucketName(), b2FileResponse.getFileName()).getResponse();
		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT, IOUtils.toString(b2DownloadFileResponse.getContent()));

		b2DownloadFileResponse = new B2DownloadFileByIdRequest(B2TestHelper.getB2AuthorizeAccountResponse(), b2FileResponse.getFileId()).getResponse();
		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT, IOUtils.toString(b2DownloadFileResponse.getContent()));

		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

	@Test
	public void testDownloadFileByRange() throws B2ApiException, IOException {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId());

		B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByNameRequest(B2TestHelper.getB2AuthorizeAccountResponse(), 
				randomPrivateBucket.getBucketName(), 
				b2FileResponse.getFileName(), 
				0, 
				5).getResponse();

		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT.substring(0, 6), IOUtils.toString(b2DownloadFileResponse.getContent()));

		b2DownloadFileResponse = new B2DownloadFileByIdRequest(B2TestHelper.getB2AuthorizeAccountResponse(), 
				b2FileResponse.getFileId(), 
				0, 
				5).getResponse();

		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT.substring(0, 6), IOUtils.toString(b2DownloadFileResponse.getContent()));

		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

}
