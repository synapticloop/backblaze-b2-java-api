package synapticloop.b2.request;

import static org.junit.Assert.*;

import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import synapticloop.b2.exception.B2ApiException;
import synapticloop.b2.response.B2AuthorizeAccountResponse;


public class B2AuthorizeAccountRequestTest {

	private static final String B2_ACCOUNT_ID = "B2_ACCOUNT_ID";
	private static final String B2_APPLICATION_KEY = "B2_APPLICATION_KEY";

	@Before
	public void setup() {
	}

	@Test
	public void testCorrectCredentials() throws Exception {
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

		B2AuthorizeAccountRequest b2AuthorizeAccountRequest = new B2AuthorizeAccountRequest(HttpClients.createDefault(), b2AccountId, b2ApplicationKey);
		B2AuthorizeAccountResponse response = b2AuthorizeAccountRequest.getResponse();
		assertNotNull(response.getAuthorizationToken());
		assertNotNull(response.getAccountId());
		assertNotNull(response.getApiUrl());
		assertNotNull(response.getDownloadUrl());
	}

	@Test (expected=B2ApiException.class)
	public void testIncorrectCredentials() throws Exception {
		B2AuthorizeAccountRequest b2AuthorizeAccountRequest = new B2AuthorizeAccountRequest(HttpClients.createDefault(), "bad", "value");
		b2AuthorizeAccountRequest.getResponse();
	}

}
