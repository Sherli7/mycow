package cm.sherli.api.mycow.amenagement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampementRepository extends JpaRepository<Campement, Long>{
	Page<Campement> findByNameContaining(String title, Pageable pagingSort);

	@Query("SELECT c FROM Campement c WHERE c.createdBy =:id")
	Page<Campement> findByCreatedBy(@Param("id") Long id, Pageable firstPageWithTwoElements);
	
	@Query("SELECT c FROM Campement c WHERE c.createdBy =:id")
	List<Campement> findByCreatedBy(@Param("id") Long id);
	
	@Query(value="SELECT c.name FROM Campement c WHERE c.id =:id")
	String findByName(@Param("id") Long id);
	
	
	   boolean existsByName(Object object);
}
