package synapticloop.b2.request;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileInfoResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2ListFilesResponse;

public class B2ListFileVersionsRequestTest {
	private static B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
	private static B2FileResponse tempFileOne = null;
	private static B2FileResponse tempFileTwo = null;
	private static B2FileResponse tempFileThree = null;
	private static B2FileResponse tempFileFour = null;
	private static String bucketId = null;

	@BeforeClass
	public static void setupBeforeClass() throws B2ApiException {
		b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
		B2BucketResponse randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
		bucketId = randomPrivateBucket.getBucketId();
		tempFileOne = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
		tempFileTwo = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
		tempFileThree = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
		tempFileFour = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
	}

	@Test
	public void listFileVersions() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(4, files.size());
	}

	@Test
	public void listFileVersionByName() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, tempFileOne.getFileName(), null, 1).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(1, files.size());

		B2FileInfoResponse b2FileInfoResponse = files.get(0);
		assertEquals(b2FileInfoResponse.getFileName(), tempFileOne.getFileName());
	}

	@Test
	public void listFileVersionByNameAndId() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, tempFileTwo.getFileName(), tempFileTwo.getFileId(), 1).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(1, files.size());

		B2FileInfoResponse b2FileInfoResponse = files.get(0);
		assertEquals(b2FileInfoResponse.getFileId(), tempFileTwo.getFileId());
	}

	@Test(expected = B2ApiException.class)
	public void listFileVersionIncorrect() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, null, tempFileTwo.getFileId(), 1).getResponse();
		List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
		assertEquals(1, files.size());

		B2FileInfoResponse b2FileInfoResponse = files.get(0);
		assertEquals(b2FileInfoResponse.getFileId(), tempFileTwo.getFileId());

	}

	@Test
	public void listFiles() throws B2ApiException {
		B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, tempFileOne.getFileName(), null, 1).getResponse();
		assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

		b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, b2ListFileVersionsResponse.getNextFileName(), null, 1).getResponse();
		assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

		b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, b2ListFileVersionsResponse.getNextFileName(), null, 1).getResponse();
		assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

		b2ListFileVersionsResponse = new B2ListFileVersionsRequest(b2AuthorizeAccountResponse, bucketId, b2ListFileVersionsResponse.getNextFileName(), null, 1).getResponse();
		assertEquals(1, b2ListFileVersionsResponse.getFiles().size());
	}

	@AfterClass
	public static void tearDownAfterClass() throws B2ApiException {
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileOne.getFileName(), tempFileOne.getFileId()).getResponse();
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileTwo.getFileName(), tempFileTwo.getFileId()).getResponse();
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileThree.getFileName(), tempFileThree.getFileId()).getResponse();
		new B2DeleteFileVersionRequest(b2AuthorizeAccountResponse, tempFileFour.getFileName(), tempFileFour.getFileId()).getResponse();
		B2TestHelper.deleteBucket(bucketId);
	}
}
