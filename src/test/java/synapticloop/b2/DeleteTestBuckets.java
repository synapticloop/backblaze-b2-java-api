package synapticloop.b2;

import java.util.List;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;
import synapticloop.b2.response.B2FileInfoResponse;

public class DeleteTestBuckets {

	public static void main(String[] args) throws B2ApiException {
		String b2AccountId = System.getenv(B2TestHelper.B2_ACCOUNT_ID);
		String b2ApplicationKey = System.getenv(B2TestHelper.B2_APPLICATION_KEY);

		boolean isOK = true;

		if(null == b2AccountId) {
			System.err.println("Could not find the environment variable '" + B2TestHelper.B2_ACCOUNT_ID + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(null == b2ApplicationKey) {
			System.err.println("Could not find the environment variable '" + B2TestHelper.B2_APPLICATION_KEY + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(!isOK) {
			System.exit(-1);
		}

		B2ApiClient b2ApiClient = new B2ApiClient(b2AccountId, b2ApplicationKey);
		List<B2BucketResponse> listBuckets = b2ApiClient.listBuckets();
		for (B2BucketResponse b2BucketResponse : listBuckets) {
			if(b2BucketResponse.getBucketName().startsWith(B2TestHelper.B2_BUCKET_PREFIX)) {
				// go through and delete all of the files
				String bucketId = b2BucketResponse.getBucketId();
				System.out.println("Deleting files in bucket '" + bucketId + "'.");
				List<B2FileInfoResponse> files = b2ApiClient.listFileVersions(bucketId).getFiles();
				for (B2FileInfoResponse b2FileInfoResponse : files) {
					String fileName = b2FileInfoResponse.getFileName();
					System.out.println("Deleting file version name: '" + fileName + "'.");
					b2ApiClient.deleteFileVersion(fileName, b2FileInfoResponse.getFileId());
				}
				System.out.println("Deleting bucket '" + bucketId + "'.");
				b2ApiClient.deleteBucket(bucketId);
			}
		}
	}

}
