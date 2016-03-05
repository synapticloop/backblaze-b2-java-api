package synapticloop.b2.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class B2ExceptionTest {
	private B2Exception b2Exception = null;

	@Before
	public void setup() {
		b2Exception = new B2Exception();
	}

	@Test
	public void testEmptyException() {
		b2Exception = new B2Exception("");
		assertEquals("", b2Exception.getMessage());
	}

	@Test
	public void testJsonException() {
		b2Exception = new B2Exception("{\n" +
				"      \"code\": \"bad_json\",\n" + 
				"      \"message\": \"unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId\",\n" + 
				"      \"status\": 400\n" + 
				"    }\n" + 
				")");
		assertEquals(400, b2Exception.getStatus());
		assertEquals("bad_json", b2Exception.getCode());
		assertEquals("unknown field in com.backblaze.modules.b2.data.FileNameAndId: accountId", b2Exception.getMessage());
	}
}
