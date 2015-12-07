package synapticloop.b2.exception;

import org.json.JSONException;
import org.json.JSONObject;

public class B2ApiException extends Exception {
	private static final long serialVersionUID = -7345341271403812967L;

	private String code = null;
	private String message = null;
	private int status = -1;
	private String originalMessage = null;

	public B2ApiException() {
		super();
	}

	public B2ApiException(String message) {
		super(message);
		parseMessage(message);
	}

	public B2ApiException(String message, Throwable cause) {
		super(message, cause);
		parseMessage(message);
	}

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

	public String getCode() { return this.code; }
	public String getMessage() { return this.message; }
	public String getOriginalMessage() { return this.originalMessage; }
	public int getStatus() { return this.status; }
}
