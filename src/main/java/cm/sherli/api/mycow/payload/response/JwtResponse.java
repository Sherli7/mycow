package cm.sherli.api.mycow.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String firstname;
  private String lastname;
  private String cni;
  private String email;
  private Long campid;
  private Long ranchid;
  private boolean requestor;
  private boolean activeAccount;
  private boolean lockedAccount;
  private List<String> roles;  



public JwtResponse(String accessToken, Long id, String firstname, String lastname, String cni, boolean activeAccount,boolean requestor,boolean lockedAccount,
		String email,long campid,long ranchid, List<String> roles) {
	this.token = accessToken;
	this.id = id;
	this.firstname = firstname;
	this.lastname = lastname;
	this.cni = cni;
	this.activeAccount = activeAccount;
	this.requestor = requestor;
	this.lockedAccount=lockedAccount;
	this.email = email;
	this.campid=campid;
	this.ranchid=ranchid;
	this.roles = roles;	
}


public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }




public boolean isRequestor() {
	return requestor;
}


public boolean isActiveAccount() {
	return activeAccount;
}


public boolean isLockedAccount() {
	return lockedAccount;
}


public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
 * @return the campid
 */
public Long getCampid() {
	return campid;
}


/**
 * @return the ranchid
 */
public Long getRanchid() {
	return ranchid;
}


public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getCni() {
    return cni;
  }

  public void setCni(String cni) {
    this.cni = cni;
  }

  public List<String> getRoles() {
    return roles;
  }
}
