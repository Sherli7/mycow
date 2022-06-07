package cm.sherli.api.mycow.log.model;

import javax.persistence.*;

import cm.sherli.api.mycow.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraitementLog extends AuditModel {
  
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;      
	private Long bovinid;
	private Long traitementid;
	@Column()
	private String issue;
		
}
