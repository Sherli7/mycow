package cm.sherli.api.mycow.sante.traitements;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import cm.sherli.api.mycow.bovin.Bovin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="traitement")
public class Traitement{

	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String libelle;
	
	private String description;

	@ManyToMany(fetch = FetchType.LAZY,
			  cascade = {
				  CascadeType.PERSIST,
				  CascadeType.MERGE
			  },
			  mappedBy = "traitements")
		  @JsonIgnore
	private Set<Bovin> bovin=new HashSet<>();
	
}
