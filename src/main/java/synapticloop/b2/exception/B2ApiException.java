package synapticloop.b2.exception;

import org.json.JSONException;
import org.json.JSONObject;

public class B2ApiException extends Exception {
	private static final long serialVersionUID = -7345341271403812967L;

	private String code = null;
	private String message = null;
	private int status = -1;
	private String originalMessage = null;

	/**
	 * Create a new B2Api Exception
	 */
	public B2ApiException() {
		super();
	}

	/**
	 * Create a new B2Api Exception with a message.  If the message is in JSON 
	 * (as returned by the backblaze B2 api) format, this message will be parsed 
	 * and the following information extracted:
	 * 
	 * <ul>
	 *   <li>status - The numeric HTTP status code. Always matches the status in the HTTP response.</li>
	 *   <li>code - A single-identifier code that identifies the error.</li>
	 *   <li>message - A human-readable message, in English, saying what went wrong.</li>
	 * </ul>
	 * 
	 * @param message The message of the exception
	 */
	public B2ApiException(String message) {
		super(message);
		parseMessage(message);
	}

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
	 * @param message The message of the exception
	 * @param cause the root cause of the exception
	 */
	public B2ApiException(String message, Throwable cause) {
		super(message, cause);
		parseMessage(message);
	}

	/**
	 * Create a new B2Api Exception with a root cause
	 * 
	 * @param cause The root cause of the exception
	 */
	public B2ApiException(Throwable cause) {
		super(cause);
	}

	private void parseMessage(String message) {
		this.originalMessage = message;

		if(null == message) {
			return;
		}

		try {
			JSONObject jsonObject = new JSONObject(message);

			String tempMessage = jsonObject.getString("message");
			if(null != tempMessage) {
				this.message = tempMessage;
			}

			this.status = jsonObject.optInt("status");
			this.code = jsonObject.optString("code");

		} catch (JSONException ex) {
			this.code = "not_json";
			this.message = message;
		}
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
	 * If the original message was in valid JSON format, return the 'message' part,
	 * else the message for the exception.
	 * 
	 * @return the message
	 */
	public String getMessage() { 
		return this.message; 
	}

	/**
	 * Return the original message for the exception.
	 * 
	 * @return the original message text
	 */
	public String getOriginalMessage() { 
		return this.originalMessage; 
	}

	/**
	 * Return the HTTP status of the returned call, or -1 if the exception was 
	 * not generated through an HTTP call.
	 * 
	 * @return the HTTP status code
	 */
	public int getStatus() {
		return this.status;
	}
}
