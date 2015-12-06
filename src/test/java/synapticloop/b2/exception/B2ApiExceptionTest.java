package synapticloop.b2.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class B2ApiExceptionTest {
	private B2ApiException b2ApiException = null;

	@Before
	public void setup() {
		b2ApiException = new B2ApiException();
	}

	@Test
	public void testEmptyException() {
		b2ApiException = new B2ApiException("");
		assertEquals("", b2ApiException.getMessage());
	}

	@Test
	public void testJsonException() {
		b2ApiException = new B2ApiException("{\n" + 
				"      \"code\": \"bad_json\",\n" + 
				"      \"message\": \"unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId\",\n" + 
				"      \"status\": 400\n" + 
				"    }\n" + 
				")");
		assertEquals(400, b2ApiException.getStatus());
		assertEquals("bad_json", b2ApiException.getCode());
		assertEquals("unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId", b2ApiException.getMessage());
	}
}
