package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2DeleteFileVersionResponse;
import synapticloop.b2.response.B2GetUploadUrlResponse;
import synapticloop.b2.response.B2UploadFileResponse;


public class B2UploadAndDeleteFileRequestTest {

	@Before
	public void setup() {
	}

	@Test
	public void testFileUpload() throws Exception {
		B2BucketResponse privateBucket = B2Helper.createRandomPrivateBucket();
		B2GetUploadUrlResponse b2GetUploadUrlResponse = B2Helper.getUploadUrl(privateBucket.getBucketId());
		File file = File.createTempFile("backblaze-api-test", ".txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("hello world!");
		fileWriter.flush();
		fileWriter.close();
		B2UploadFileResponse b2UploadFileResponse = new B2UploadFileRequest(B2Helper.getB2AuthorizeAccountResponse(), b2GetUploadUrlResponse, file).getResponse();

		String fileName = b2UploadFileResponse.getFileName();
		String fileId = b2UploadFileResponse.getFileId();

		// now we need t delete the file as well to clean up after ourselves

		B2DeleteFileVersionResponse b2DeleteFileVersionResponse = new B2DeleteFileVersionRequest(B2Helper.getB2AuthorizeAccountResponse(), fileName, fileId).getResponse();
		assertEquals(fileName, b2DeleteFileVersionResponse.getFileName());
		assertEquals(fileId, b2DeleteFileVersionResponse.getFileId());

		B2Helper.deleteBucket(privateBucket.getBucketId());
	}

}
