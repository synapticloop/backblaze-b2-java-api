package synapticloop.b2.response;

import org.json.JSONException;
import org.json.JSONObject;

import synapticloop.b2.exception.B2Exception;

public abstract class BaseB2Response {

	protected JSONObject response;

	public BaseB2Response(final String json) throws B2Exception {
		this(parse(json));
	}

	public BaseB2Response(final JSONObject response) throws B2Exception {
		this.response = response;
	}

	/**
	 * Parse a string into a JSON object 
	 * 
	 * @param json the data to parse to an object
	 * 
	 * @return the parsed JSON object
	 * 
	 * @throws B2Exception if there was an error parsing the object
	 */
	private static JSONObject parse(String json) throws B2Exception {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException ex) {
			throw new B2Exception(ex);
		}
		return jsonObject;
	}
}
