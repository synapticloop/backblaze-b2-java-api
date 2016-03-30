package synapticloop.b2.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class B2ListFilesResponseTest {

	@Test
	public void testGetFiles() throws Exception {
		final String json = "{\n" +
				"  \"files\": [\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"e73f8339c3e731e3fd9b0bec46222bd0016f1afa\",\n" +
				"      \"contentType\": \"image/jpeg\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f112de56cdcbb09e8_d20160120_m172133_c000_v0001010_t0006\",\n" +
				"      \"fileInfo\": {\n" +
				"        \"src_last_modified_millis\": \"0\"\n" +
				"      },\n" +
				"      \"fileName\": \"IMG_5066.jpg\",\n" +
				"      \"size\": 180903,\n" +
				"      \"uploadTimestamp\": 1453310493000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"89d2b373a7b26dbec209fe2db5c0ca6557cb1a8d\",\n" +
				"      \"contentType\": \"video/mp4\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f1092070825b2ec14_d20151219_m191222_c000_v0001014_t0038\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"adele/BBC.Music.Presents.Adele.at.the.BBC.2015.HDTV.x264-NoGRP.mp4\",\n" +
				"      \"size\": 536107168,\n" +
				"      \"uploadTimestamp\": 1450552342000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"7201a995b6841a84fe23086b33d50e49a627fabe\",\n" +
				"      \"contentType\": \"application/octet-stream\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f109c8fe9ff88885f_d20160106_m005102_c000_v0001014_t0041\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"b2sync.tar.gz\",\n" +
				"      \"size\": 1515462,\n" +
				"      \"uploadTimestamp\": 1452041462000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"0a9d332d09376d28cf04726e146aeedc546b09cf\",\n" +
				"      \"contentType\": \"image/png\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f10695c6a45107f47_d20151227_m200935_c000_v0001014_t0026\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"logo/selligy-icon-square_360.png\",\n" +
				"      \"size\": 7583,\n" +
				"      \"uploadTimestamp\": 1451246975000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"f2cf229c6657ca2b17afb9af22090c92cf9a7d2f\",\n" +
				"      \"contentType\": \"application/octet-stream\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f1189dc406f02d4d9_d20160104_m040533_c000_v0001014_t0004\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"uploads-large.tar.gz\",\n" +
				"      \"size\": 236324399,\n" +
				"      \"uploadTimestamp\": 1451880333000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"7d9133df91610fd610d817b316db01c68b988dfd\",\n" +
				"      \"contentType\": \"application/octet-stream\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f1189dc406f02d4d7_d20160104_m040413_c000_v0001014_t0004\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"uploads-small.tar.gz\",\n" +
				"      \"size\": 1216666,\n" +
				"      \"uploadTimestamp\": 1451880253000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"07ecb3b3f48025118b314f5c7e169b0ca96bc3f1\",\n" +
				"      \"contentType\": \"application/octet-stream\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f1189dc406f02d4d8_d20160104_m040432_c000_v0001014_t0004\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"uploads-smallmedium.tar.gz\",\n" +
				"      \"size\": 40941493,\n" +
				"      \"uploadTimestamp\": 1451880272000\n" +
				"    },\n" +
				"    {\n" +
				"      \"action\": \"upload\",\n" +
				"      \"contentSha1\": \"b63f91f826e87887f4ce5c28ee6988cab8a0f3f4\",\n" +
				"      \"contentType\": \"application/octet-stream\",\n" +
				"      \"fileId\": \"4_z181632c04c2ddde855010b15_f1189dc406f02d4db_d20160104_m043616_c000_v0001014_t0004\",\n" +
				"      \"fileInfo\": {},\n" +
				"      \"fileName\": \"uploads-tiny.tar.gz\",\n" +
				"      \"size\": 1130,\n" +
				"      \"uploadTimestamp\": 1451882176000\n" +
				"    }\n" +
				"  ],\n" +
				"  \"nextFileId\": null,\n" +
				"  \"nextFileName\": null\n" +
				"}";
		final B2ListFilesResponse response = new B2ListFilesResponse(json);
		assertNotNull(response);
		assertNotNull(response.getFiles());
		assertFalse(response.getFiles().isEmpty());
		assertEquals(8, response.getFiles().size());
		assertNull(response.getNextFileId());
		assertNull(response.getNextFileName());
	}
}