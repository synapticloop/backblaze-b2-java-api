package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileResponse;

public class B2GetFileInfoRequestTest {

	@Test
	public void testGetFileInfo() throws B2ApiException {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2Helper.getB2AuthorizeAccountResponse();
		B2BucketResponse b2BucketResponse = B2Helper.createRandomPrivateBucket();
		B2FileResponse b2FileResponseIn = B2Helper.uploadTemporaryFileToBucket(b2BucketResponse.getBucketId());

		B2FileResponse b2FileResponseOut = new B2GetFileInfoRequest(b2AuthorizeAccountResponse, b2FileResponseIn.getFileId()).getResponse();

		assertEquals(b2FileResponseIn.getContentLength(), b2FileResponseOut.getContentLength());
		assertEquals(b2FileResponseIn.getContentSha1(), b2FileResponseOut.getContentSha1());
		assertEquals(b2FileResponseIn.getContentType(), b2FileResponseOut.getContentType());
		assertEquals(b2FileResponseIn.getFileId(), b2FileResponseOut.getFileId());
		assertEquals(b2FileResponseIn.getFileName(), b2FileResponseOut.getFileName());

		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, b2FileResponseOut.getFileName(), b2FileResponseOut.getFileId()).getResponse();

		B2Helper.deleteBucket(b2BucketResponse.getBucketId());
	}

}
