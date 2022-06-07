package cm.sherli.api.mycow.payload.request;


import java.util.Set;

import javax.validation.constraints.*;

import cm.sherli.api.mycow.amenagement.Ranch;

public class SignupRequest {
  @NotBlank

  private String firstname;

  private String lastname;


  private String cni;

  private String email;

  private Set<String> role;

  private boolean employee;
  
  private String password;
  private Ranch ranch;


public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public boolean isEmployee() {
    return employee;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
