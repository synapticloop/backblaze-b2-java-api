package synapticloop.b2.request;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2AuthorizeAccountResponse;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileInfoResponse;
import synapticloop.b2.response.B2FileResponse;
import synapticloop.b2.response.B2ListFilesResponse;

import static org.junit.Assert.assertEquals;

public class B2ListFileVersionsRequestTest {
    private static B2AuthorizeAccountResponse b2AuthorizeAccountResponse = null;
    private static B2FileResponse tempFileOne = null;
    private static B2FileResponse tempFileTwo = null;
    private static B2FileResponse tempFileThree = null;
    private static B2FileResponse tempFileFour = null;
    private static String bucketId = null;

    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        b2AuthorizeAccountResponse = B2TestHelper.getB2AuthorizeAccountResponse();
        B2BucketResponse randomPrivateBucket = B2TestHelper.createRandomPrivateBucket();
        bucketId = randomPrivateBucket.getBucketId();
        tempFileOne = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
        tempFileTwo = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
        tempFileThree = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
        tempFileFour = B2TestHelper.uploadTemporaryFileToBucket(bucketId);
    }

    @Test
    public void listFileVersions() throws Exception {
        B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketId, 1000).getResponse();
        List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
        assertEquals(4, files.size());
    }

    @Test
    public void listFileVersionByName() throws Exception {
        B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketId, 1, tempFileOne.getFileName(), null, null, null).getResponse();
        List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
        assertEquals(1, files.size());

        B2FileInfoResponse b2FileInfoResponse = files.get(0);
        assertEquals(b2FileInfoResponse.getFileName(), tempFileOne.getFileName());
    }

    @Test
    public void listFileVersionByNameAndId() throws Exception {
        B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketId, 1, tempFileTwo.getFileName(), tempFileTwo.getFileId(), null, null).getResponse();
        List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
        assertEquals(1, files.size());

        B2FileInfoResponse b2FileInfoResponse = files.get(0);
        assertEquals(b2FileInfoResponse.getFileId(), tempFileTwo.getFileId());
    }

    @Test(expected = B2ApiException.class)
    public void listFileVersionIncorrect() throws Exception {
        B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(HttpClients.createDefault(), b2AuthorizeAccountResponse, bucketId, 1, null, tempFileTwo.getFileId(), null, null).getResponse();
        List<B2FileInfoResponse> files = b2ListFileVersionsResponse.getFiles();
        assertEquals(1, files.size());

        B2FileInfoResponse b2FileInfoResponse = files.get(0);
        assertEquals(b2FileInfoResponse.getFileId(), tempFileTwo.getFileId());

    }

    @Test
    public void listFiles() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        B2ListFilesResponse b2ListFileVersionsResponse = new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, 1, tempFileOne.getFileName(), null, null, null).getResponse();
        assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

        b2ListFileVersionsResponse = new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, 1, b2ListFileVersionsResponse.getNextFileName(), null, null, null).getResponse();
        assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

        b2ListFileVersionsResponse = new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, 1, b2ListFileVersionsResponse.getNextFileName(), null, null, null).getResponse();
        assertEquals(1, b2ListFileVersionsResponse.getFiles().size());

        b2ListFileVersionsResponse = new B2ListFileVersionsRequest(client, b2AuthorizeAccountResponse, bucketId, 1, b2ListFileVersionsResponse.getNextFileName(), null, null, null).getResponse();
        assertEquals(1, b2ListFileVersionsResponse.getFiles().size());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        new B2DeleteFileVersionRequest(client, b2AuthorizeAccountResponse, tempFileOne.getFileName(), tempFileOne.getFileId()).getResponse();
        new B2DeleteFileVersionRequest(client, b2AuthorizeAccountResponse, tempFileTwo.getFileName(), tempFileTwo.getFileId()).getResponse();
        new B2DeleteFileVersionRequest(client, b2AuthorizeAccountResponse, tempFileThree.getFileName(), tempFileThree.getFileId()).getResponse();
        new B2DeleteFileVersionRequest(client, b2AuthorizeAccountResponse, tempFileFour.getFileName(), tempFileFour.getFileId()).getResponse();
        B2TestHelper.deleteBucket(bucketId);
    }
}
