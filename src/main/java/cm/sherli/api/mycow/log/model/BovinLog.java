package cm.sherli.api.mycow.log.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cm.sherli.api.mycow.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BovinLog extends AuditModel {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;      
	private Long bovinid; 
	private String action;
	private String madeBy;
	private String madeAt; 
}
