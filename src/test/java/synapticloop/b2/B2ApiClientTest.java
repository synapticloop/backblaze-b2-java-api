package synapticloop.b2;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.helper.B2TestHelper;
import synapticloop.b2.response.B2BucketResponse;

public class B2ApiClientTest {
	public static final String B2_ACCOUNT_ID = "B2_ACCOUNT_ID";
	public static final String B2_APPLICATION_KEY = "B2_APPLICATION_KEY";
	private B2ApiClient client;

	@Before
	public void setup() throws Exception {
		boolean isOK = true;
		String b2AccountId = System.getenv(B2_ACCOUNT_ID);
		String b2ApplicationKey = System.getenv(B2_APPLICATION_KEY);

		if(null == b2AccountId) {
			System.err.println("Could not find the environment variable '" + B2_ACCOUNT_ID + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(null == b2ApplicationKey) {
			System.err.println("Could not find the environment variable '" + B2_APPLICATION_KEY + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(!isOK) {
			System.exit(-1);
		}

		client = new B2ApiClient();
		client.authenticate(b2AccountId, b2ApplicationKey);
	}

	@Test
	public void testPrivateBuckets() throws Exception {
		B2BucketResponse createPrivateBucket = client.createBucket(B2TestHelper.B2_BUCKET_PREFIX + UUID.randomUUID().toString(), BucketType.allPrivate);

		B2BucketResponse updateBucket = client.updateBucket(createPrivateBucket.getBucketId(), BucketType.allPublic);
		assertEquals(updateBucket.getBucketType(), BucketType.allPublic);

		client.deleteBucket(createPrivateBucket.getBucketId());
	}

	@Test
	public void testPublicBuckets() throws Exception {
		B2BucketResponse createPublicBucket = client.createBucket(B2TestHelper.B2_BUCKET_PREFIX + UUID.randomUUID().toString(), BucketType.allPublic);
		client.deleteBucket(createPublicBucket.getBucketId());
	}

}
