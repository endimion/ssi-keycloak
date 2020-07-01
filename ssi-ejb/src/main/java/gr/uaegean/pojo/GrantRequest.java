package gr.uaegean.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GrantRequest {
	
	private String username;
	private String password;
	@JsonProperty("grant_type")
	private String grantType;
	
	public GrantRequest(String username, String password, String grantType) {
		this.username = username;
		this.password = password;
		this.grantType = grantType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

}
