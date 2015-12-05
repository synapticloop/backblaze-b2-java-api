package synapticloop.b2;

import org.apache.commons.codec.binary.Base64;

public class AuthorizationHeader {
	private String applicationKey = null;
	private String accountId = null;

	public AuthorizationHeader(String accountId, String applicationKey) {
		this.accountId = accountId;
		this.applicationKey = applicationKey;
	}

	public String getHeader() {
		return("Basic " + Base64.encodeBase64String((accountId + ":" + applicationKey).getBytes()));
	}
}
