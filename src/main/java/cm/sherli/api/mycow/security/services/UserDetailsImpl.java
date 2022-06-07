package cm.sherli.api.mycow.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cm.sherli.api.mycow.employee.Employee;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String firstname;

  private String lastname;
  
  private String cni;
  
  private String email;
  
  private boolean requestor;
  private boolean activeAccount;
  private boolean lockedAccount;
  
  private Long campid;
  
  private Long ranchid;
  

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String firstname,String lastname,String cni, String email,boolean activeAccount,boolean requestor,boolean lockedAccount, 
		  String password,Long campid,Long ranchid,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstname = firstname;
    this.lastname=lastname;
    this.cni=cni;
    this.email = email;
    this.activeAccount=activeAccount;
    this.requestor=requestor;
    this.lockedAccount=lockedAccount;
    this.password = password;
    this.authorities = authorities;
    this.campid=campid;
    this.ranchid=ranchid;
  }

  public static UserDetailsImpl build(Employee user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(), 
        user.getFirstname(),
        user.getLastname(),
        user.getCni(),
        user.getEmail(),
        user.isActiveAccount(),
        user.isRequestor(),
        user.isLockedAccount(),
        user.getPassword(), 
    	user.getCampid(),
    	user.getRanchid(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
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

@Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getFirstname() {
	return firstname;
}

public String getLastname() {
	return lastname;
}

public String getCni() {
	return cni;
}

@Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
