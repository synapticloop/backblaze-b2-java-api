package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.*;
import synapticloop.b2.util.ChecksumHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class B2DeleteFileVersionRequestTest {

	@Test
	public void testDeleteFileVersion() throws B2ApiException {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		B2BucketResponse b2BucketResponse = B2TestHelper.createRandomPrivateBucket();
		B2FileResponse b2FileResponseIn = B2TestHelper.uploadTemporaryFileToBucket(b2BucketResponse.getBucketId());

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(HttpClients.createDefault(),
				b2AuthorizeAccountResponse, b2FileResponseIn.getFileName(), b2FileResponseIn.getFileId()).getResponse();

		assertEquals(b2FileResponseIn.getFileId(), b2DeleteFileVersionResponse.getFileId());
		assertEquals(b2FileResponseIn.getFileName(), b2DeleteFileVersionResponse.getFileName());

		B2TestHelper.deleteBucket(b2BucketResponse.getBucketId());
	}

	@Test
	public void testDeleteFileVersionWithWhitespace() throws B2ApiException {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		B2BucketResponse b2BucketResponse = B2TestHelper.createRandomPrivateBucket();

		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(b2BucketResponse.getBucketId());
		File file;
		try {
			file = File.createTempFile("test/path/backblaze-api-test whitespace", ".txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("hello whitespace!");
			fileWriter.flush();
			fileWriter.close();
			file.deleteOnExit();
		} catch(IOException ioex) {
			throw new B2ApiException("Could not create temporary file", ioex);
		}
		final CloseableHttpClient client = HttpClients.createDefault();
		final B2FileResponse b2FileResponseIn = new B2UploadFileRequest(client, b2AuthorizeAccountResponse,
				b2GetUploadUrlResponse, file.getName(), file, ChecksumHelper.calculateSha1(file)).getResponse();

		final B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByIdRequest(client, b2AuthorizeAccountResponse, b2FileResponseIn.getFileId()).getResponse();
		assertEquals(b2FileResponseIn.getFileId(), b2DownloadFileResponse.getFileId());
		assertNotNull(b2DownloadFileResponse.getContent());

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(client,
				b2AuthorizeAccountResponse, b2FileResponseIn.getFileName(), b2FileResponseIn.getFileId()).getResponse();

		assertEquals(b2FileResponseIn.getFileId(), b2DeleteFileVersionResponse.getFileId());
		assertEquals(b2FileResponseIn.getFileName(), b2DeleteFileVersionResponse.getFileName());

		B2TestHelper.deleteBucket(b2BucketResponse.getBucketId());
	}

}
