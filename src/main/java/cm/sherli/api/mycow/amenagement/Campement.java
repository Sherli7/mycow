package cm.sherli.api.mycow.amenagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cm.sherli.api.mycow.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


//@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "campements")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Campement extends AuditModel{


/**
* 
*/
	
	private static final long serialVersionUID = 1L;

@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private double area;
  
  private String longitude;

  private String latitude;

  private Long ranchid;
  
	  
}
