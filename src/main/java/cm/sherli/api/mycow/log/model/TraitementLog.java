package cm.sherli.api.mycow.log.model;

import javax.persistence.*;

import cm.sherli.api.mycow.common.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraitementLog {
  
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;      
	private Long bovinid;
	private String treatname;
	@Column()
	private String issue;
	private String createdAt;
}
