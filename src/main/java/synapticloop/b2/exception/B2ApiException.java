package synapticloop.b2.exception;

public class B2ApiException extends Exception {
	private static final long serialVersionUID = -7345341271403812967L;

	public B2ApiException() {
		super();
	}

	public B2ApiException(String message) {
		super(message);
	}

	public B2ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public B2ApiException(Throwable cause) {
		super(cause);
	}
}
