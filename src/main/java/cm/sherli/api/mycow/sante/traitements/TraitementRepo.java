package cm.sherli.api.mycow.sante.traitements;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraitementRepo extends JpaRepository<Traitement, Long> {
	public List<Traitement> findByLibelle(String libelle);
	Boolean existsByLibelle(String libelle);
	
	
	//public List<Insemination> existsBycodeSemence(String codeSemence);
	@Query(value="SELECT c FROM traitement c WHERE c.createdBy =:id",nativeQuery=true)
	Page<Traitement> findByCreatedBy(@Param("id") Long id, Pageable page);
	
	@Query(value="SELECT c FROM traitement c WHERE c.campid =:id",nativeQuery=true)
	List<Traitement> findByCreatedBy(@Param("id") Long id);
	
}
