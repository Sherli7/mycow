package cm.sherli.api.mycow.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.group.Groupes;
import cm.sherli.api.mycow.group.Role;


@Entity
@Table(name = "employees")
public class Employee extends AuditModel{
		  @Id
		  @GeneratedValue(strategy = GenerationType.IDENTITY)
		  private Long id;

		  private String firstname;
		  @Column(nullable = true)
		  private String lastname;

		  private String cni;

		  private String email;
		  
		  @Column(name = "requestor", nullable=true)
		  private boolean requestor;
		  @Column(name = "active_account", nullable=true)
		  private boolean activeAccount;
		  @Column(name = "locked_account", nullable=true)
		  private boolean lockedAccount;
		  
		  private String password;

		  @ManyToMany(fetch = FetchType.LAZY)
		  @JoinTable(  name = "user_roles", 
		        joinColumns = @JoinColumn(name = "user_id"), 
		        inverseJoinColumns = @JoinColumn(name = "role_id"))
		  private Set<Role> roles = new HashSet<>(); 

			@ManyToMany(fetch = FetchType.LAZY,
				      cascade = {
				          CascadeType.PERSIST,
				          CascadeType.MERGE
				      })
				  @JoinTable(name = "employee_groups",
				        joinColumns = { @JoinColumn(name = "employee_id") },
				        inverseJoinColumns = { @JoinColumn(name = "group_id") })
			@JsonBackReference	  
			private Set<Groupes> groupes = new HashSet<>();

		  @Column(nullable = true, updatable = false)
		  private Long campid;

		  @Column(nullable = true, updatable = false)
		  private Long ranchid;
		  
		  @ManyToMany(fetch = FetchType.LAZY)
		  @JoinTable(  name = "troupeau_employe", 
		        joinColumns = @JoinColumn(name = "employee_id"), 
		        inverseJoinColumns = @JoinColumn(name = "troupeau_id"))
		  private Set<Troupeau> troupeau = new HashSet<>();
		  		  
		  public Employee() {
		  }

		public Employee(String firstname, String lastname, String cni, String email, boolean activeAccount,
				boolean requestor,boolean locked) {
			this.firstname = firstname;
			this.lastname = lastname;
			this.cni = cni;
			this.email = email;
			this.activeAccount=activeAccount;
			this.requestor=requestor;
			this.lockedAccount=locked;
			
		}


		public Employee(String firstname, String lastname, String cni, String email, boolean activeAccount,
				boolean requestor, boolean locked,String password, Set<Role> roles, Set<Groupes> groupes, Long campid, Long ranchid,
				Set<Troupeau> troupeau) {
			super();
			this.firstname = firstname;
			this.lastname = lastname;
			this.cni = cni;
			this.email = email;
			this.requestor=requestor;
			this.activeAccount=activeAccount;
			this.lockedAccount=locked;
			this.password = password;
			this.roles = roles;
			this.groupes = groupes;
			this.campid = campid;
			this.ranchid = ranchid;
			this.troupeau = troupeau;
		}

		public boolean isRequestor() {
			return requestor;
		}

		public void setRequestor(boolean requestor) {
			this.requestor = requestor;
		}

		public boolean isActiveAccount() {
			return activeAccount;
		}

		public void setActiveAccount(boolean activeAccount) {
			this.activeAccount = activeAccount;
		}

		public boolean isLockedAccount() {
			return lockedAccount;
		}

		public void setLockedAccount(boolean lockedAccount) {
			this.lockedAccount = lockedAccount;
		}

		public Long getRanchid() {
			return ranchid;
		}


		public void setRanchid(Long ranchid) {
			this.ranchid = ranchid;
		}


		public Set<Groupes> getGroupes() {
			return groupes;
		}


		public void setGroupes(Set<Groupes> groupes) {
			this.groupes = groupes;
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



		  public Long getId() {
		    return id;
		  }

		  public void setId(Long id) {
		    this.id = id;
		  }

		  
		public Long getCampid() {
			return campid;
		}

		public void setCampid(Long campid) {
			this.campid = campid;
		}


		public Set<Troupeau> getTroupeau() {
			return troupeau;
		}


		public void setTroupeau(Set<Troupeau> troupeau) {
			this.troupeau = troupeau;
		}


		public String getEmail() {
		    return email;
		  }

		  public void setEmail(String email) {
		    this.email = email;
		  }

		 @JsonIgnore
		  public String getPassword() {
		    return password;
		  }

		 
		  public void setPassword(String password) {
		    this.password = password;
		  }
		  
		  

		  public Set<Role> getRoles() {
		    return roles;
		  }


		public void setRoles(Set<Role> roles) {
		    this.roles = roles;
		  }
		  
		  public void addRole(Role role) {
			    this.roles.add(role);
			    role.getEmployees().add(this);
		 }
		  
		  public void addGroup(Groupes group) {
			    this.groupes.add(group);
			    group.getEmployee().add(this);
		 }
		  
		  public void addTroupeau(Troupeau troup) {
			    this.troupeau.add(troup);
			    troup.getEmployee().add(this);
		 }		 

		  
		public void removeRole(long roleId) {
			    Role rol = this.roles.stream().filter(t -> t.getId() == roleId).findFirst().orElse(null);
			    if (rol != null) this.roles.remove(rol);
			    rol.getEmployees().remove(this);
			  }
		
		public void removeGroup(long roleId) {
		    Groupes g = this.groupes.stream().filter(t -> t.getId() == roleId).findFirst().orElse(null);
		    if (g != null) this.groupes.remove(g);
		    g.getEmployee().remove(this);
		  }
		
		public void removeTroupeau(long roleId) {
		    Troupeau troup = this.troupeau.stream().filter(t -> roleId == t.getId()).findFirst().orElse(null);
		    //if (troup != null) this.troupeau.remove(troup);
			if (troup != null) {
				troup.getEmployee().remove(this);
			}
		}

		@Override
		public String toString() {
			return "Employee [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", cni=" + cni
					+ ", email=" + email + ", requestor=" + requestor + ", activeAccount=" + activeAccount
					+ ", lockedAccount=" + lockedAccount + ", password=" + password + ", roles=" + roles + ", groupes="
					+ groupes + ", campid=" + campid + ", ranchid=" + ranchid + ", troupeau=" + troupeau + "]";
		}

}