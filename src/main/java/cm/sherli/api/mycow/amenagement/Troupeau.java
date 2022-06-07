package cm.sherli.api.mycow.amenagement;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.employee.Employee;
import cm.sherli.api.mycow.reproduction.Insemination;
import cm.sherli.api.mycow.sante.traitements.Traitement;
import cm.sherli.api.mycow.sante.vaccin.Vaccin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)

public class Troupeau extends AuditModel {

	/**
	 * @author sherli7
	 * @since 18 April 2022
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String description;
	private Long ranchid;
	private Long campid;
	
	  @ManyToMany(fetch = FetchType.LAZY,
			  cascade = {
			      CascadeType.PERSIST,
			      CascadeType.MERGE
			  },
			  mappedBy = "troupeau")
	  @JsonIgnore
	  private Set<Employee> employee= new HashSet<>();

	  
}
