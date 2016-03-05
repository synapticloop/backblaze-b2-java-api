package synapticloop.b2.util;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class HelperTest {
	// this is taken from the URL https://www.backblaze.com/b2/docs/string_encoding.html

	private static final String ENCODING_TEST_JSON = "[\n" + 
			"  {\"fullyEncoded\": \"%20\", \"minimallyEncoded\": \"+\", \"string\": \" \"},\n" + 
			"  {\"fullyEncoded\": \"%21\", \"minimallyEncoded\": \"!\", \"string\": \"!\"},\n" + 
			"  {\"fullyEncoded\": \"%22\", \"minimallyEncoded\": \"%22\", \"string\": \"\\\"\"},\n" + 
			"  {\"fullyEncoded\": \"%23\", \"minimallyEncoded\": \"%23\", \"string\": \"#\"},\n" + 
			"  {\"fullyEncoded\": \"%24\", \"minimallyEncoded\": \"$\", \"string\": \"$\"},\n" + 
			"  {\"fullyEncoded\": \"%25\", \"minimallyEncoded\": \"%25\", \"string\": \"%\"},\n" + 
			"  {\"fullyEncoded\": \"%26\", \"minimallyEncoded\": \"%26\", \"string\": \"&\"},\n" + 
			"  {\"fullyEncoded\": \"%27\", \"minimallyEncoded\": \"'\", \"string\": \"'\"},\n" + 
			"  {\"fullyEncoded\": \"%28\", \"minimallyEncoded\": \"(\", \"string\": \"(\"},\n" + 
			"  {\"fullyEncoded\": \"%29\", \"minimallyEncoded\": \")\", \"string\": \")\"},\n" + 
			"  {\"fullyEncoded\": \"%2A\", \"minimallyEncoded\": \"*\", \"string\": \"*\"},\n" + 
			"  {\"fullyEncoded\": \"%2B\", \"minimallyEncoded\": \"%2B\", \"string\": \"+\"},\n" + 
			"  {\"fullyEncoded\": \"%2C\", \"minimallyEncoded\": \"%2C\", \"string\": \",\"},\n" + 
			"  {\"fullyEncoded\": \"%2D\", \"minimallyEncoded\": \"-\", \"string\": \"-\"},\n" + 
			"  {\"fullyEncoded\": \"%2E\", \"minimallyEncoded\": \".\", \"string\": \".\"},\n" + 
			"  {\"fullyEncoded\": \"/\", \"minimallyEncoded\": \"/\", \"string\": \"/\"},\n" + 
			"  {\"fullyEncoded\": \"%30\", \"minimallyEncoded\": \"0\", \"string\": \"0\"},\n" + 
			"  {\"fullyEncoded\": \"%31\", \"minimallyEncoded\": \"1\", \"string\": \"1\"},\n" + 
			"  {\"fullyEncoded\": \"%32\", \"minimallyEncoded\": \"2\", \"string\": \"2\"},\n" + 
			"  {\"fullyEncoded\": \"%33\", \"minimallyEncoded\": \"3\", \"string\": \"3\"},\n" + 
			"  {\"fullyEncoded\": \"%34\", \"minimallyEncoded\": \"4\", \"string\": \"4\"},\n" + 
			"  {\"fullyEncoded\": \"%35\", \"minimallyEncoded\": \"5\", \"string\": \"5\"},\n" + 
			"  {\"fullyEncoded\": \"%36\", \"minimallyEncoded\": \"6\", \"string\": \"6\"},\n" + 
			"  {\"fullyEncoded\": \"%37\", \"minimallyEncoded\": \"7\", \"string\": \"7\"},\n" + 
			"  {\"fullyEncoded\": \"%38\", \"minimallyEncoded\": \"8\", \"string\": \"8\"},\n" + 
			"  {\"fullyEncoded\": \"%39\", \"minimallyEncoded\": \"9\", \"string\": \"9\"},\n" + 
			"  {\"fullyEncoded\": \"%3A\", \"minimallyEncoded\": \":\", \"string\": \":\"},\n" + 
			"  {\"fullyEncoded\": \"%3B\", \"minimallyEncoded\": \";\", \"string\": \";\"},\n" + 
			"  {\"fullyEncoded\": \"%3C\", \"minimallyEncoded\": \"%3C\", \"string\": \"<\"},\n" + 
			"  {\"fullyEncoded\": \"%3D\", \"minimallyEncoded\": \"=\", \"string\": \"=\"},\n" + 
			"  {\"fullyEncoded\": \"%3E\", \"minimallyEncoded\": \"%3E\", \"string\": \">\"},\n" + 
			"  {\"fullyEncoded\": \"%3F\", \"minimallyEncoded\": \"%3F\", \"string\": \"?\"},\n" + 
			"  {\"fullyEncoded\": \"%40\", \"minimallyEncoded\": \"@\", \"string\": \"@\"},\n" + 
			"  {\"fullyEncoded\": \"%41\", \"minimallyEncoded\": \"A\", \"string\": \"A\"},\n" + 
			"  {\"fullyEncoded\": \"%42\", \"minimallyEncoded\": \"B\", \"string\": \"B\"},\n" + 
			"  {\"fullyEncoded\": \"%43\", \"minimallyEncoded\": \"C\", \"string\": \"C\"},\n" + 
			"  {\"fullyEncoded\": \"%44\", \"minimallyEncoded\": \"D\", \"string\": \"D\"},\n" + 
			"  {\"fullyEncoded\": \"%45\", \"minimallyEncoded\": \"E\", \"string\": \"E\"},\n" + 
			"  {\"fullyEncoded\": \"%46\", \"minimallyEncoded\": \"F\", \"string\": \"F\"},\n" + 
			"  {\"fullyEncoded\": \"%47\", \"minimallyEncoded\": \"G\", \"string\": \"G\"},\n" + 
			"  {\"fullyEncoded\": \"%48\", \"minimallyEncoded\": \"H\", \"string\": \"H\"},\n" + 
			"  {\"fullyEncoded\": \"%49\", \"minimallyEncoded\": \"I\", \"string\": \"I\"},\n" + 
			"  {\"fullyEncoded\": \"%4A\", \"minimallyEncoded\": \"J\", \"string\": \"J\"},\n" + 
			"  {\"fullyEncoded\": \"%4B\", \"minimallyEncoded\": \"K\", \"string\": \"K\"},\n" + 
			"  {\"fullyEncoded\": \"%4C\", \"minimallyEncoded\": \"L\", \"string\": \"L\"},\n" + 
			"  {\"fullyEncoded\": \"%4D\", \"minimallyEncoded\": \"M\", \"string\": \"M\"},\n" + 
			"  {\"fullyEncoded\": \"%4E\", \"minimallyEncoded\": \"N\", \"string\": \"N\"},\n" + 
			"  {\"fullyEncoded\": \"%4F\", \"minimallyEncoded\": \"O\", \"string\": \"O\"},\n" + 
			"  {\"fullyEncoded\": \"%50\", \"minimallyEncoded\": \"P\", \"string\": \"P\"},\n" + 
			"  {\"fullyEncoded\": \"%51\", \"minimallyEncoded\": \"Q\", \"string\": \"Q\"},\n" + 
			"  {\"fullyEncoded\": \"%52\", \"minimallyEncoded\": \"R\", \"string\": \"R\"},\n" + 
			"  {\"fullyEncoded\": \"%53\", \"minimallyEncoded\": \"S\", \"string\": \"S\"},\n" + 
			"  {\"fullyEncoded\": \"%54\", \"minimallyEncoded\": \"T\", \"string\": \"T\"},\n" + 
			"  {\"fullyEncoded\": \"%55\", \"minimallyEncoded\": \"U\", \"string\": \"U\"},\n" + 
			"  {\"fullyEncoded\": \"%56\", \"minimallyEncoded\": \"V\", \"string\": \"V\"},\n" + 
			"  {\"fullyEncoded\": \"%57\", \"minimallyEncoded\": \"W\", \"string\": \"W\"},\n" + 
			"  {\"fullyEncoded\": \"%58\", \"minimallyEncoded\": \"X\", \"string\": \"X\"},\n" + 
			"  {\"fullyEncoded\": \"%59\", \"minimallyEncoded\": \"Y\", \"string\": \"Y\"},\n" + 
			"  {\"fullyEncoded\": \"%5A\", \"minimallyEncoded\": \"Z\", \"string\": \"Z\"},\n" + 
			"  {\"fullyEncoded\": \"%5B\", \"minimallyEncoded\": \"%5B\", \"string\": \"[\"},\n" + 
			"  {\"fullyEncoded\": \"%5C\", \"minimallyEncoded\": \"%5C\", \"string\": \"\\\\\"},\n" + 
			"  {\"fullyEncoded\": \"%5D\", \"minimallyEncoded\": \"%5D\", \"string\": \"]\"},\n" + 
			"  {\"fullyEncoded\": \"%5E\", \"minimallyEncoded\": \"%5E\", \"string\": \"^\"},\n" + 
			"  {\"fullyEncoded\": \"%5F\", \"minimallyEncoded\": \"_\", \"string\": \"_\"},\n" + 
			"  {\"fullyEncoded\": \"%60\", \"minimallyEncoded\": \"%60\", \"string\": \"`\"},\n" + 
			"  {\"fullyEncoded\": \"%61\", \"minimallyEncoded\": \"a\", \"string\": \"a\"},\n" + 
			"  {\"fullyEncoded\": \"%62\", \"minimallyEncoded\": \"b\", \"string\": \"b\"},\n" + 
			"  {\"fullyEncoded\": \"%63\", \"minimallyEncoded\": \"c\", \"string\": \"c\"},\n" + 
			"  {\"fullyEncoded\": \"%64\", \"minimallyEncoded\": \"d\", \"string\": \"d\"},\n" + 
			"  {\"fullyEncoded\": \"%65\", \"minimallyEncoded\": \"e\", \"string\": \"e\"},\n" + 
			"  {\"fullyEncoded\": \"%66\", \"minimallyEncoded\": \"f\", \"string\": \"f\"},\n" + 
			"  {\"fullyEncoded\": \"%67\", \"minimallyEncoded\": \"g\", \"string\": \"g\"},\n" + 
			"  {\"fullyEncoded\": \"%68\", \"minimallyEncoded\": \"h\", \"string\": \"h\"},\n" + 
			"  {\"fullyEncoded\": \"%69\", \"minimallyEncoded\": \"i\", \"string\": \"i\"},\n" + 
			"  {\"fullyEncoded\": \"%6A\", \"minimallyEncoded\": \"j\", \"string\": \"j\"},\n" + 
			"  {\"fullyEncoded\": \"%6B\", \"minimallyEncoded\": \"k\", \"string\": \"k\"},\n" + 
			"  {\"fullyEncoded\": \"%6C\", \"minimallyEncoded\": \"l\", \"string\": \"l\"},\n" + 
			"  {\"fullyEncoded\": \"%6D\", \"minimallyEncoded\": \"m\", \"string\": \"m\"},\n" + 
			"  {\"fullyEncoded\": \"%6E\", \"minimallyEncoded\": \"n\", \"string\": \"n\"},\n" + 
			"  {\"fullyEncoded\": \"%6F\", \"minimallyEncoded\": \"o\", \"string\": \"o\"},\n" + 
			"  {\"fullyEncoded\": \"%70\", \"minimallyEncoded\": \"p\", \"string\": \"p\"},\n" + 
			"  {\"fullyEncoded\": \"%71\", \"minimallyEncoded\": \"q\", \"string\": \"q\"},\n" + 
			"  {\"fullyEncoded\": \"%72\", \"minimallyEncoded\": \"r\", \"string\": \"r\"},\n" + 
			"  {\"fullyEncoded\": \"%73\", \"minimallyEncoded\": \"s\", \"string\": \"s\"},\n" + 
			"  {\"fullyEncoded\": \"%74\", \"minimallyEncoded\": \"t\", \"string\": \"t\"},\n" + 
			"  {\"fullyEncoded\": \"%75\", \"minimallyEncoded\": \"u\", \"string\": \"u\"},\n" + 
			"  {\"fullyEncoded\": \"%76\", \"minimallyEncoded\": \"v\", \"string\": \"v\"},\n" + 
			"  {\"fullyEncoded\": \"%77\", \"minimallyEncoded\": \"w\", \"string\": \"w\"},\n" + 
			"  {\"fullyEncoded\": \"%78\", \"minimallyEncoded\": \"x\", \"string\": \"x\"},\n" + 
			"  {\"fullyEncoded\": \"%79\", \"minimallyEncoded\": \"y\", \"string\": \"y\"},\n" + 
			"  {\"fullyEncoded\": \"%7A\", \"minimallyEncoded\": \"z\", \"string\": \"z\"},\n" + 
			"  {\"fullyEncoded\": \"%7B\", \"minimallyEncoded\": \"%7B\", \"string\": \"{\"},\n" + 
			"  {\"fullyEncoded\": \"%7C\", \"minimallyEncoded\": \"%7C\", \"string\": \"|\"},\n" + 
			"  {\"fullyEncoded\": \"%7D\", \"minimallyEncoded\": \"%7D\", \"string\": \"}\"},\n" + 
			"  {\"fullyEncoded\": \"%7E\", \"minimallyEncoded\": \"~\", \"string\": \"~\"},\n" + 
			"  {\"fullyEncoded\": \"%7F\", \"minimallyEncoded\": \"%7F\", \"string\": \"\\u007f\"},\n" + 
			"  {\"fullyEncoded\": \"%E8%87%AA%E7%94%B1\", \"minimallyEncoded\": \"%E8%87%AA%E7%94%B1\", \"string\": \"\\u81ea\\u7531\"},\n" + 
			"  {\"fullyEncoded\": \"%F0%90%90%80\", \"minimallyEncoded\": \"%F0%90%90%80\", \"string\": \"\\ud801\\udc00\"}\n" + 
			"]";

	@Test
	public void testEncodingFileName() throws JSONException {
		Helper.urlEncode("");
		JSONArray jsonArray = new JSONArray(ENCODING_TEST_JSON);
		int length = jsonArray.length();
		for(int i = 0; i< length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String fullyEncoded = jsonObject.getString("fullyEncoded");
			String minimallyEncoded = jsonObject.getString("minimallyEncoded");
			String string = jsonObject.getString("string");

			// assert encoding
			String urlEncode = Helper.urlEncodeFileName(string);
			if(!(minimallyEncoded.equals(urlEncode) || fullyEncoded.equals(urlEncode))) {
				assertTrue("Invalid encoding, could not encode '" + 
						string + 
						"' to either '" + 
						minimallyEncoded + 
						"' or '" + 
						fullyEncoded + 
						"'.  The encoded output was '" +
						"'" +
						urlEncode + 
						"'.", 
						false);
			}

			// assert decoding
			assertEquals(string, Helper.urlDecode(fullyEncoded)); 
			assertEquals(string, Helper.urlDecode(minimallyEncoded));

		}
	}
	@Test
	public void testEncoding() throws JSONException {
		Helper.urlEncode("");
		JSONArray jsonArray = new JSONArray(ENCODING_TEST_JSON);
		int length = jsonArray.length();
		for(int i = 0; i< length; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String fullyEncoded = jsonObject.getString("fullyEncoded");
			String minimallyEncoded = jsonObject.getString("minimallyEncoded");
			String string = jsonObject.getString("string");

			// assert encoding
			if(!"/".equals(string)) {
				String urlEncode = Helper.urlEncode(string);
				if(!(minimallyEncoded.equals(urlEncode) || fullyEncoded.equals(urlEncode))) {
					assertTrue("Invalid encoding, could not encode '" + 
							string + 
							"' to either '" + 
							minimallyEncoded + 
							"' or '" + 
							fullyEncoded + 
							"'.  The encoded output was '" +
							"'" +
							urlEncode + 
							"'.", 
							false);
				}

				// assert decoding
				assertEquals(string, Helper.urlDecode(fullyEncoded)); 
				assertEquals(string, Helper.urlDecode(minimallyEncoded));
			}

		}
	}

}
