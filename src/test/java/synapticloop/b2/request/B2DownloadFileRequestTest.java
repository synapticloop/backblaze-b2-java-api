package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2DownloadFileResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.util.ChecksumHelper;

public class B2DownloadFileRequestTest {
	private B2BucketResponse randomPrivateBucket = null;
	private B2FileResponse b2FileResponse = null;

	@Test
	public void testDownloadByFilePath() throws Exception {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2TestHelper.getUploadUrl(randomPrivateBucket.getBucketId());
		File file = File.createTempFile("backblaze-api-test", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello world!");
		fileWriter.flush();
		fileWriter.close();

		String testFileName = "some/file/path/" + file.getName();
		B2FileResponse b2UploadFileResponse = new B2UploadFileRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), 
				b2GetUploadUrlResponse, 
				testFileName, file, ChecksumHelper.calculateSha1(file)).getResponse();

		String fileName = b2UploadFileResponse.getFileName();
		String fileId = b2UploadFileResponse.getFileId();

		B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByNameRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), 
				randomPrivateBucket.getBucketName(), 
				testFileName).getResponse();
		assertEquals(fileName, b2DownloadFileResponse.getFileName());
		// now we need to delete the file as well to clean up after ourselves

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(HttpClients.createDefault(), 
				B2TestHelper.getB2AuthorizeAccountResponse(), 
				testFileName, 
				fileId).getResponse();

		assertEquals(fileName, b2DeleteFileVersionResponse.getFileName());
		assertEquals(fileId, b2DeleteFileVersionResponse.getFileId());

		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

	@Test
	public void testDownloadFileBy() throws Exception {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId());

		B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByNameRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), randomPrivateBucket.getBucketName(), b2FileResponse.getFileName()).getResponse();
		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT, IOUtils.toString(b2DownloadFileResponse.getContent()));

		b2DownloadFileResponse = new B2DownloadFileByIdRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(), b2FileResponse.getFileId()).getResponse();
		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT, IOUtils.toString(b2DownloadFileResponse.getContent()));

		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

	@Test
	public void testDownloadFileByRange() throws Exception {
		randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		b2FileResponse = B2TestHelper.uploadTemporaryFileToBucket(randomPrivateBucket.getBucketId());

		B2DownloadFileResponse b2DownloadFileResponse = new B2DownloadFileByNameRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(),
				randomPrivateBucket.getBucketName(), 
				b2FileResponse.getFileName(), 
				0, 
				5).getResponse();

		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT.substring(0, 6), IOUtils.toString(b2DownloadFileResponse.getContent()));

		b2DownloadFileResponse = new B2DownloadFileByIdRequest(HttpClients.createDefault(), B2TestHelper.getB2AuthorizeAccountResponse(),
				b2FileResponse.getFileId(), 
				0, 
				5).getResponse();

		assertEquals(B2TestHelper.DUMMY_FILE_CONTENT.substring(0, 6), IOUtils.toString(b2DownloadFileResponse.getContent()));

		B2TestHelper.deleteFile(b2FileResponse.getFileName(), b2FileResponse.getFileId());
		B2TestHelper.deleteBucket(randomPrivateBucket.getBucketId());
	}

}
