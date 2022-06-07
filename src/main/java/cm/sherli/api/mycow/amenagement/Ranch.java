package cm.sherli.api.mycow.amenagement;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cm.sherli.api.mycow.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//@JsonIgnoreProperties({"hibernateLazyInitializer"})

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
public class Ranch extends AuditModel{


	/**
	 * 
	 */
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  
	  private String name;
	  
	  private double area;
	  
	  private String longitude;
	  
	  private String latitude;
	  
	 
}
