package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuth {
	
	@JsonProperty("access_token")
	private String accessToken;
	private String scope;
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
	public OAuth() {
		
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "OAuth{" +
               "access_token='" + accessToken + '\'' +
               ", scope='" + scope + '\'' +
               ", token_type='" + tokenType + '\'' +
               ", expires_in='" + expiresIn + '\'' +
               '}';
	}
	
	
}
