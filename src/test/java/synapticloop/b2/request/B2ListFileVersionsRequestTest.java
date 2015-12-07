package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2Helper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileInfoResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2ListFilesResponse;

public class B2ListFileVersionsRequestTest {
	private static B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private static B2FileResponse tempFileOne = null;
	private static B2FileResponse tempFileTwo = null;
	private static String bucketId = null;

	@BeforeClass
	public static void setup() throws B2ApiException {
		b2AuthorizeAccountResponse = B2Helper.getB2AuthorizeAccountResponse();
		B2BucketResponse randomPrivateBucket = B2Helper.createRandomPrivateBucket();
		bucketId = randomPrivateBucket.getBucketId();
		tempFileOne = B2Helper.uploadTemporaryFileToBucket(bucketId);
		tempFileTwo = B2Helper.uploadTemporaryFileToBucket(bucketId);
	}

	@Test
	public void listFileVersions() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(2, files.size());
	}

	@Test
	public void listFileVersionByName() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, tempFileOne.getFileName(), null, 100).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(1, files.size());

		B2FileInfoResponse b2FileInfoResponse = files.get(0);
		assertEquals(b2FileInfoResponse.getFileName(), tempFileOne.getFileName());
	}

	@Test
	public void listFileVersionById() {
		try {
			B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, tempFileTwo.getFileName(), tempFileTwo.getFileId(), 100).getResponse();
			List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
			assertEquals(1, files.size());
	
			B2FileInfoResponse b2FileInfoResponse = files.get(0);
			assertEquals(b2FileInfoResponse.getFileId(), tempFileTwo.getFileId());
		} catch(B2ApiException ex) {
			System.out.println(ex.getCode());
			System.out.println(ex.getMessage());
			System.out.println(ex.getOriginalMessage());
			System.out.println(ex.getStatus());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws B2ApiException {
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileOne.getFileName(), tempFileOne.getFileId()).getResponse();
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileTwo.getFileName(), tempFileTwo.getFileId()).getResponse();
		B2Helper.deleteBucket(bucketId);
	}
}
