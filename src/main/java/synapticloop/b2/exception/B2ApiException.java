package synapticloop.b2.exception;

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

import org.json.JSONException;
import org.json.JSONObject;

public class B2ApiException extends Exception {
	private static final long serialVersionUID = -7345341271403812967L;

	private String code;
	private String message;
	private int status;

	/**
	 * Retry-After header value (in seconds)
	 */
	private Integer retry;

	private final String json;

	/**
	 * Create a new B2Api Exception with a message and root cause.  If the message
	 * is in JSON (as returned by the backblaze B2 api) format, this message will
	 * be parsed and the following information extracted:
	 *
	 * <ul>
	 *   <li>status - The numeric HTTP status code. Always matches the status in the HTTP response.</li>
	 *   <li>code - A single-identifier code that identifies the error.</li>
	 *   <li>message - A human-readable message, in English, saying what went wrong.</li>
	 * </ul>
	 *
	 * @param json The message of the exception
	 * @param cause the root cause of the exception
	 */
	public B2ApiException(String json, Throwable cause) {
		super(json, cause);
		this.json = json;
		this.parse(json);
	}


	private void parse(String json) {
		if (null != json) {
			try {
				JSONObject jsonObject = new JSONObject(json);
				this.message = jsonObject.optString("message", null);
				this.status = jsonObject.optInt("status", -1);
				this.code = jsonObject.optString("code", null);

			} catch (JSONException ex) {
				// Ignore
				this.message = json;
			}
		}
	}

	/**
	 * @param retry Value in seconds
	 * 
	 * @return the exception with retry
	 */
	public B2ApiException withRetry(Integer retry) {
		this.retry = retry;
		return this;
	}

	/**
	 * Return the backblaze error code
	 *
	 * @return the backblaze error code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * If the original message was in valid JSON format, return the 'message'
	 * part, else the message for the exception.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Return the original message for the exception, which is a JSON formatted
	 * string
	 *
	 * @return the original message text
	 */
	public String getJson() {
		return this.json;
	}

	/**
	 * Return the HTTP status of the returned call, or null if the exception was
	 * not generated through an HTTP call.
	 *
	 * @return the HTTP status code
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Retry-After header value (in seconds)
	 * 
	 * @return Value in seconds or null if not Retry-After header in response
	 */
	public Integer getRetry() {
		return retry;
	}
}
