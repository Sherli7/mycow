package cm.sherli.api.mycow.reproduction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cm.sherli.api.mycow.amenagement.Troupeau;

public interface InseminationRepo extends JpaRepository<Insemination, Long> {

	List<Insemination> findBycodeSemence(String title);

	@Query(value="SELECT * FROM insemination c WHERE c.code_semence=:codeSemence and c.status='attente'",nativeQuery=true)
	 boolean existsBycodeSemence(@Param("codeSemence")String codeSemence);
	
	@Query(value="SELECT * FROM insemination c WHERE c.status='success'",nativeQuery=true)
	 boolean existsBystatussuccess();
	
	@Query(value="SELECT * FROM insemination c WHERE c.status='success'",nativeQuery=true)
	List<Insemination> findBystatus();
	
	//public List<Insemination> existsBycodeSemence(String codeSemence);
	@Query(value="SELECT c FROM insemination c WHERE c.created_by =:id",nativeQuery=true)
	Page<Insemination> findByCreatedBy(@Param("id") Long id, Pageable page);
	
	@Query(value="SELECT c FROM insemination c WHERE c.created_by =:id",nativeQuery=true)
	List<Insemination> findByCreatedBy(@Param("id") Long id);


	@Query(value="SELECT * FROM insemination c WHERE c.status='success'",nativeQuery=true)
	Page<Insemination> findsuccessInsem(Pageable page);


	@Query(value="SELECT * FROM insemination c WHERE c.status='awaiting'",nativeQuery=true)
	Page<Insemination> findAwaitingInsem(Pageable page);
	
	
}