package synapticloop.b2;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class B2ApiClientTest {
	private B2ApiClient b2ApiClient;

	@Before
	public void setup() {
		b2ApiClient = new B2ApiClient(null, null);
	}

	@Test
	public void testClient() {
		assertNull(null);
	}

}
