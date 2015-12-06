package synapticloop.b2.response;

import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public abstract class BaseB2Response {
	protected static final String KEY_AUTHORIZATION_TOKEN = "authorizationToken";
	protected static final String KEY_UPLOAD_URL = "uploadUrl";
	protected static final String KEY_BUCKET_ID = "bucketId";

	public static JSONObject getParsedResponse(InputStream inputStream) throws B2ApiException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(convertStreamToString(inputStream));
		} catch (JSONException ex) {
			throw new B2ApiException(ex);
		}
		return(jsonObject);
	}

	public static JSONObject getParsedResponse(String data) throws B2ApiException {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
		} catch (JSONException ex) {
			throw new B2ApiException(ex);
		}
		return(jsonObject);
	}

	private static String convertStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\\A");
		String tempValue = scanner.hasNext() ? scanner.next() : "";
		scanner.close();
		return(tempValue);
	}

}
