package cm.sherli.api.mycow.reproduction;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.bovin.Bovin;

@Entity
@Table(name="Insemination")
public class Insemination extends AuditModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String codeSemence;
	private String uniqueidFemale; 
	private String uniqueidMale;
	private String status;
	private String dateInsem;
	private String dateFec;
	private Long personnel;
	

	
	@ManyToMany(fetch = FetchType.LAZY,
			  cascade = {
				  CascadeType.PERSIST,
				  CascadeType.MERGE
			  },
			  mappedBy = "insemination")
		  @JsonIgnore
		private Set<Bovin> bovin=new HashSet<>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCodeSemence() {
		return codeSemence;
	}


	public void setCodeSemence(String codeSemence) {
		this.codeSemence = codeSemence;
	}


	public String getUniqueidFemale() {
		return uniqueidFemale;
	}


	public void setUniqueidFemale(String uniqueidFemale) {
		this.uniqueidFemale = uniqueidFemale;
	}


	public String getUniqueidMale() {
		return uniqueidMale;
	}


	public void setUniqueidMale(String uniqueidMale) {
		this.uniqueidMale = uniqueidMale;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDateInsem() {
		return dateInsem;
	}


	public void setDateInsem(String dateInsem) {
		this.dateInsem = dateInsem;
	}


	public String getDateFec() {
		return dateFec;
	}


	public void setDateFec(String dateFec) {
		this.dateFec = dateFec;
	}


	public Long getPersonnel() {
		return personnel;
	}


	public void setPersonnel(Long personnel) {
		this.personnel = personnel;
	}


	public Set<Bovin> getBovin() {
		return bovin;
	}


	public void setBovin(Set<Bovin> bovin) {
		this.bovin = bovin;
	}

		 
}