package cm.sherli.api.mycow.log.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinLog {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;      
	private String vaccin;
	private String labo; 
	private String action;
	private String madeBy;
	private String madeAt;
}
