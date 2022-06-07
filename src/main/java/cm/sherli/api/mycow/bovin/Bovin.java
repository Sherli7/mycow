package cm.sherli.api.mycow.bovin;

import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.audit.AuditModel;
import cm.sherli.api.mycow.reproduction.Insemination;
import cm.sherli.api.mycow.sante.diseases.Disease;
import cm.sherli.api.mycow.sante.traitements.Traitement;
import cm.sherli.api.mycow.sante.vaccin.Vaccin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Table(name = "bovins")
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bovin extends AuditModel{
	/**
	 *
	 */
	private static final long serialVersionUID = 5547563237496471954L;
	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bovinid;
	private String uniqueid;
	private String firstPhysicId;
	private String secPhysicId;
	private String birthDay;
	private String sex;
	private String modeReproduction;
	private Double weightAtBirth;
	private Double heightAtBirth;
	private String race;
	private String robe;
	private String cornage;
	private boolean status;
	private String country;
	private boolean isDelete=false;
	@Column(name = "estMalade")
	private boolean sante;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "troupeauid", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@ToString.Exclude
	private Troupeau troupeau;


	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "bovins_malade",
			joinColumns = { @JoinColumn(name = "diseaseid") },
			inverseJoinColumns = { @JoinColumn(name = "bovinid") })
	@ToString.Exclude
	private Set<Disease> diseases = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "bovins_traites",
			joinColumns = { @JoinColumn(name = "traitementid") },
			inverseJoinColumns = { @JoinColumn(name = "bovinid") })
	@ToString.Exclude
	private Set<Traitement> traitements = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "bovins_vaccines",
			joinColumns = { @JoinColumn(name = "vaccinid") },
			inverseJoinColumns = { @JoinColumn(name = "bovinid") })
	@ToString.Exclude
	private Set<Vaccin> vaccin = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "bovins_insemine",
			joinColumns = { @JoinColumn(name = "inseminationid") },
			inverseJoinColumns = { @JoinColumn(name = "bovinid") })
	@ToString.Exclude
	private Set<Insemination> insemination = new HashSet<>();


	//ADD OPERATIONS: BEGIN
	public void addInsemination(Insemination insem) {
		this.insemination.add(insem);
		insem.getBovin().add(this);
	}

	public void addVaccination(Vaccin vac) {
		this.vaccin.add(vac);
		vac.getBovin().add(this);
	}

	public void addTraitement(Traitement trait) {
		this.traitements.add(trait);
		trait.getBovin().add(this);
	}

	public void addDisease(Disease mal) {
		this.diseases.add(mal);
		mal.getBovin().add(this);
	}
//ADD OPERATIONS:END

	//REMOVE OPERATIONS: BEGIN
	public void removeDisease(long diseaseId) {
		Disease diseas = this.diseases.stream().filter(t -> t.getId() == diseaseId).findFirst().orElse(null);
		if (diseas != null) this.diseases.remove(diseas);
		diseas.getBovin().remove(this);
	}

	public void removeTraitement(long traitId) {
		Traitement trait = this.traitements.stream().filter(t -> t.getId() == traitId).findFirst().orElse(null);
		if (trait != null) this.traitements.remove(traitId);
		trait.getBovin().remove(this);
	}
	public void removeVaccin(long vacId) {
		Vaccin vac = this.vaccin.stream().filter(t -> t.getId() == vacId).findFirst().orElse(null);
		if (vac != null) this.vaccin.remove(vacId);
		vac.getBovin().remove(this);
	}
	public void removeInsem(long insemId) {
		Insemination insem = this.insemination.stream().filter(t -> t.getId() == insemId).findFirst().orElse(null);
		if (insem != null) this.insemination.remove(insem);
		insem.getBovin().remove(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Bovin bovin = (Bovin) o;
		return bovinid != null && Objects.equals(bovinid, bovin.bovinid);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}