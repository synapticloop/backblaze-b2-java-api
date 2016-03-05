package synapticloop.b2.response;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import synapticloop.b2.exception.B2Exception;

public abstract class BaseB2Response {
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
