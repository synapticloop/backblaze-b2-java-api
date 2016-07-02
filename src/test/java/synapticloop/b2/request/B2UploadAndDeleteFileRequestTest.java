package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.util.ChecksumHelper;


public class B2UploadAndDeleteFileRequestTest {

	@Test
	public void testFileUpload() throws Exception {
		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();
		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(privateBucket.getBucketId());
		File file = File.createTempFile("backblaze-api-test", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello world!");
		fileWriter.flush();
		fileWriter.close();
		B2FileResponse b2UploadFileResponse = new B2UploadFileRequest(HttpClients.createDefault(),
				B2TestHelper.getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file.getName(), file,
				ChecksumHelper.calculateSha1(file)).getResponse();

		String fileName = b2UploadFileResponse.getFileName();
		String fileId = b2UploadFileResponse.getFileId();

		// now we need to delete the file as well to clean up after ourselves

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), fileName, fileId).getResponse();
		assertEquals(fileName, b2DeleteFileVersionResponse.getFileName());
		assertEquals(fileId, b2DeleteFileVersionResponse.getFileId());

		B2TestHelper.deleteBucket(privateBucket.getBucketId());
	}

	@Test
	public void testFileUploadFailureTestMode() throws Exception {
		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();
		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(privateBucket.getBucketId());
		File file = File.createTempFile("backblaze-api-test", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello world!");
		fileWriter.flush();
		fileWriter.close();
		while (true) {
			try {
				final B2UploadFileRequest request = new B2UploadFileRequest(HttpClients.createDefault(),
						B2TestHelper.getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file.getName(), file,
						ChecksumHelper.calculateSha1(file));
				request.addHeader("X-Bz-Test-Mode", "fail_some_uploads");
				B2FileResponse b2UploadFileResponse = request.getResponse();

				String fileName = b2UploadFileResponse.getFileName();
				String fileId = b2UploadFileResponse.getFileId();

				// now we need to delete the file as well to clean up after ourselves

				B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), fileName, fileId).getResponse();
				assertEquals(fileName, b2DeleteFileVersionResponse.getFileName());
				assertEquals(fileId, b2DeleteFileVersionResponse.getFileId());
			} catch (B2ApiException e) {
				assertEquals(503, e.getStatus());
				break;
			}
		}

		B2TestHelper.deleteBucket(privateBucket.getBucketId());
	}
	@Test
	public void testFileUploadWithInfo() throws Exception {
		B2BucketResponse privateBucket = B2TestHelper.createRandomPrivateBucket();
		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(privateBucket.getBucketId());
		Map<String, String> fileInfo = new HashMap<String, String>();
		fileInfo.put("hello", "world");

		File file = File.createTempFile("backblaze-api-test", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello world!");
		fileWriter.flush();
		fileWriter.close();

		B2FileResponse b2UploadFileResponse = new B2UploadFileRequest(HttpClients.createDefault(),
				B2TestHelper.getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file.getName(), file,
				ChecksumHelper.calculateSha1(file), fileInfo).getResponse();

		String fileName = b2UploadFileResponse.getFileName();
		String fileId = b2UploadFileResponse.getFileId();

		// try and get the info for the file
		B2FileResponse b2FileInfoResponse = new B2GetFileInfoRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), b2UploadFileResponse.getFileId()).getResponse();
		assertEquals("world", b2FileInfoResponse.getFileInfo().get("hello"));

		// now we need to delete the file as well to clean up after ourselves

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), fileName, fileId).getResponse();
		assertEquals(fileName, b2DeleteFileVersionResponse.getFileName());
		assertEquals(fileId, b2DeleteFileVersionResponse.getFileId());

		B2TestHelper.deleteBucket(privateBucket.getBucketId());
	}
}
