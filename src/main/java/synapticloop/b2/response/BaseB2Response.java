package synapticloop.b2.response;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.b2.exception.B2Exception;

public abstract class BaseB2Response {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseB2Response.class);

	protected JSONObject response;

	/**
	 * Create a new B2 Response object by parsing the passed in String
	 * 
	 * @param json the Response (in json format)
	 * 
	 * @throws B2Exception if there was an error in the parsing of the reponse
	 */
	public BaseB2Response(final String json) throws B2Exception {
		this(parse(json));
	}

	/**
	 * Create a new B2 Response with a pre parsed JSONObject response
	 * 
	 * @param responsem the pre-parsed json object
	 * 
	 * @throws B2Exception if there was an error in the response
	 */

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

	/**
	 * Parse through the expected keys to determine whether any of the keys in 
	 * the response will not be mapped.  This will loop through the JSON object 
	 * and any key left in the object will generate a 'WARN' message.  The 
	 * response class __MUST__ remove the object (i.e. jsonObject.remove(KEY_NAME))
	 * after getting the value
	 * 
	 * @param LOGGER The logger to use
	 * @param jsonObject the parsed response as a json Object
	 */
	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys(Logger LOGGER, JSONObject jsonObject) {
		if(LOGGER.isWarnEnabled()) {
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				LOGGER.warn("Found an unexpected json key of '{}', this is not mapped to a field...", key);
			}
		}
	}

}
