package synapticloop.b2.response;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class B2FileInfoResponseTest {

	@Test
	public void testGetFileInfo() throws Exception {
		final String json =
				"    {\n" +
						"      \"action\": \"upload\",\n" +
						"      \"contentSha1\": \"f66ccfc5920c4527a3473cac8418e759fc991a1c\",\n" +
						"      \"contentType\": \"image/jpeg\",\n" +
						"      \"fileId\": \"4_zd866c2e0ac0d7d4855110b15_f10583db69f768e88_d20160122_m203004_c000_v0001016_t0021\",\n" +
						"      \"fileInfo\": {\n" +
						"        \"meta1\": \"This is some text for meta1.\",\n" +
						"        \"meta2\": \"This is some more text for meta2.\",\n" +
						"        \"unicorns-and-rainbows\": \"test header\"\n" +
						"      },\n" +
						"      \"fileName\": \"IMG_4666.jpg\",\n" +
						"      \"size\": 1828156,\n" +
						"      \"uploadTimestamp\": 1453494604000\n" +
						"    }";
		final B2FileInfoResponse response = new B2FileInfoResponse(new JSONObject(json));
		assertNotNull(response);
		assertNotNull(response.getFileId());
		assertNull(response.getContentLength());
		assertNotNull(response.getAction());
		assertNotNull(response.getFileName());
		assertNotNull(response.getContentSha1());
		assertNotNull(response.getSize());
		assertNotNull(response.getFileInfo());
		assertFalse(response.getFileInfo().isEmpty());
		assertNotNull(response.getFileInfo().get("meta1"));
		assertNotNull(response.getFileInfo().get("meta1"));
		assertNotNull(response.getFileInfo().get("meta2"));
		assertNotNull(response.getFileInfo().get("unicorns-and-rainbows"));
	}
}