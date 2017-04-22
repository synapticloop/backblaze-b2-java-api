package synapticloop.b2.response;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import synapticloop.b2.exception.B2ApiException;

public abstract class BaseB2Response {
	private final JSONObject response;

	/**
	 * Create a new B2 Response object by parsing the passed in String
	 *
	 * @param json the Response (in json format)
	 * @throws B2ApiException if there was an error in the parsing of the response
	 */
	public BaseB2Response(final String json) throws B2ApiException {
		this(parse(json));
	}

	/**
	 * Create a new B2 Response with a pre parsed JSONObject response
	 *
	 * @param response the pre-parsed json object
	 * @throws B2ApiException if there was an error in the response
	 */

	public BaseB2Response(final JSONObject response) throws B2ApiException {
		this.response = response;
	}

	/**
	 * Parse a string into a JSON object
	 *
	 * @param json the data to parse to an object
	 * @return the parsed JSON object
	 * @throws B2ApiException if there was an error parsing the object
	 */
	private static JSONObject parse(String json) throws B2ApiException {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException ex) {
			throw new B2ApiException(json, ex);
		}
		return jsonObject;
	}

	/**
	 * Read and remove String with key from JSON
	 * 
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(String key) {
		return this.readString(response, key);
	}

	/**
	 * Read and remove String with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a string and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected String readString(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value.toString();
	}

	/**
	 * Read and remove int with key from JSON object
	 * 
	 * @param key the key to read as an int and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected Integer readInt(String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof Number ? ((Number) value).intValue() : Integer.parseInt(value.toString());
	}

	/**
	 * Read and remove long with key from JSON object
	 * 
	 * @param key the key to read as a long and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected Long readLong(String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof Number ? ((Number) value).longValue() : Long.parseLong(value.toString());
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(String key) {
		return this.readObject(response, key);
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 * 
	 * @param response The JSON object to read from
	 * @param key the key to read as a JSONObject and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONObject readObject(JSONObject response, String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONObject ? (JSONObject) value : null;
	}

	/**
	 * Read and remove JSONArray with key from JSON object
	 * 
	 * @param key the key to read as a JSONArray and remove
	 * 
	 * @return the read key (or null if it doesn't exist)
	 */
	protected JSONArray readObjects(String key) {
		final Object value = response.remove(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		return value instanceof JSONArray ? (JSONArray) value : null;
	}

	/**
	 * Read and remove JSONObject with key from JSON object
	 *
	 * @param key the key to read as a JSONObject and put keys and values into map
	 *
	 * @return the read keys and values (or null if it doesn't exist)
	 */
	protected Map<String, String> readMap(String key) {
		final Map<String, String> map = new HashMap<String, String>();
		JSONObject value = this.readObject(key);
		if (null == value || JSONObject.NULL == value) {
			getLogger().warn("No field for key {}", key);
			return null;
		}
		for (String k:  value.keySet().toArray(new String[value.keySet().size()])) {
			map.put(k, this.readString(value, k));
		}
		return map;
	}

	/**
	 * Parse through the expected keys to determine whether any of the keys in
	 * the response will not be mapped.  This will loop through the JSON object
	 * and any key left in the object will generate a 'WARN' message.  The
	 * response class __MUST__ remove the object (i.e. jsonObject.remove(KEY_NAME))
	 * after getting the value, or use the utility methods in this class.  This 
	 * is used more as a testing tool/sanity test than anything else as there 
	 * are some instances in where keys are returned, however are not listed in 
	 * the documentation.
	 * 
	 * {@link BaseB2Response#readInt(String)}
	 * {@link BaseB2Response#readLong(String)}
	 * {@link BaseB2Response#readString(String)}
	 * {@link BaseB2Response#readObject(String)}
	 */
	@SuppressWarnings("rawtypes")
	protected void warnOnMissedKeys() {
		if (getLogger().isWarnEnabled()) {
			Iterator keys = response.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				if(B2ResponseProperties.KEY_ACCOUNT_ID.equals(key)) {
					getLogger().warn("Found an unexpected key of '{}' in JSON that is not mapped to a field, with value '{}'.", key, "[redacted]");
				} else {
					getLogger().warn("Found an unexpected key of '{}' in JSON that is not mapped to a field, with value '{}'.", key, response.get(key));
				}
			}
		}
	}

	/**
	 * Get the logger to use for Logging from the base class
	 * 
	 * @return The logger to use
	 */
	protected abstract Logger getLogger();
}
