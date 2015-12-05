package synapticloop.b2.response;

import java.io.InputStream;

import org.json.JSONObject;

import synapticloop.b2.exception.B2ApiException;

public class B2AuthorizeAccountResponse extends BaseB2Response {
	private String accountId = null;
	private String apiUrl = null;
	private String authorizationToken = null;
	private String downloadUrl = null;

	public B2AuthorizeAccountResponse(InputStream inputStream) throws B2ApiException {
		JSONObject jsonObject = getParsedResponse(inputStream);

		this.accountId = jsonObject.optString("accountId");
		this.apiUrl = jsonObject.optString("apiUrl");
		this.authorizationToken = jsonObject.optString("authorizationToken");
		this.downloadUrl = jsonObject.optString("downloadUrl");
	}

	public String getAccountId() { return this.accountId; }
	public String getApiUrl() { return this.apiUrl; }
	public String getAuthorizationToken() { return this.authorizationToken; }
	public String getDownloadUrl() { return this.downloadUrl; }
}
