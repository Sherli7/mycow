package cm.sherli.api.mycow.group;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import cm.sherli.api.mycow.employee.Employee;

@Entity
@Table(name = "groups")
@JsonInclude(JsonInclude.Include.ALWAYS)

public class Groupes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;
  

  @Column(name = "description", nullable=true)
  private String description;


  @ManyToMany(fetch = FetchType.LAZY,
	      cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      },
	      mappedBy = "groupes")
	  private Set<Employee> employee = new HashSet<>();

  
  public Groupes(String name, String description) {
	this.name = name;
	this.description = description;
}

public Groupes() {

  }
  
  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

public Set<Employee> getEmployee() {
	return employee;
}

public void setEmployee(Set<Employee> employee) {
	this.employee = employee;
}

public void setId(long id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
 
  
}