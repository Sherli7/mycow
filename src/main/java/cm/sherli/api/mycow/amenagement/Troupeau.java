package cm.sherli.api.mycow.amenagement;

import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Troupeau extends AuditModel {

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
	  @ToString.Exclude
	  private Set<Employee> employee= new HashSet<>();

}
