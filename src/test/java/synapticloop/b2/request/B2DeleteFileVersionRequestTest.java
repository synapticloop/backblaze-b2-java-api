package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.util.ChecksumHelper;

public class B2DeleteFileVersionRequestTest {

	@Test
	public void testDeleteFileVersion() throws Exception {
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
	public void testDeleteFileVersionWithWhitespace() throws Exception {
		B2AuthorizeAccountResponse b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		B2BucketResponse b2BucketResponse = B2TestHelper.createRandomPrivateBucket();

		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(b2BucketResponse.getBucketId());
		File file;
		file = File.createTempFile("test/path/backblaze-api-test whitespace", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello whitespace!");
		fileWriter.flush();
		fileWriter.close();
		file.deleteOnExit();
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
