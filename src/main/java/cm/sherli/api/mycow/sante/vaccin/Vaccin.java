package cm.sherli.api.mycow.sante.vaccin;

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

import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.bovin.Bovin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class Vaccin extends AuditModel{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	private String name;
	private String description;
	private String labo;
	@Column()
	private boolean prohibited;
	@ManyToMany(fetch = FetchType.LAZY,
			  cascade = {
				  CascadeType.PERSIST,
				  CascadeType.MERGE
			  },
			  mappedBy = "vaccin")
	@JsonIgnoreProperties
	private Set<Bovin> bovin=new HashSet<>();

}
