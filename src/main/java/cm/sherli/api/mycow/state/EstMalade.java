package cm.sherli.api.mycow.state;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.bovin.Bovin;
import cm.sherli.api.mycow.sante.diseases.Disease;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class EstMalade extends AuditModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    @JoinColumn(name = "diseaseid")
    Long disease;
    
    @JoinColumn(name = "bovinid")
    Long bovinid;
    
    public EstMalade() {}
    
	public EstMalade(Long disease, Long bovinid) {
		this.disease = disease;
		this.bovinid = bovinid;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDisease() {
		return disease;
	}

	public void setDisease(Long disease) {
		this.disease = disease;
	}



	public Long getBovinid() {
		return bovinid;
	}
	public void setBovinid(Long bovinid) {
		this.bovinid = bovinid;
	}
    
    
	
}
