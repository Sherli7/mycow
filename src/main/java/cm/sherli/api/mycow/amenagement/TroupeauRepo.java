package cm.sherli.api.mycow.amenagement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cm.sherli.api.mycow.bovin.Bovin;

public interface TroupeauRepo extends JpaRepository<Troupeau, Long> {
	List<Troupeau> findByName(String name);
	Page<Troupeau> findByNameContaining(String title, Pageable pagingSort);
	
	@Query("SELECT c FROM Troupeau c WHERE c.createdBy =:id")
	Page<Troupeau> findByCreatedBy(@Param("id") Long id, Pageable firstPageWithTwoElements);
	
	@Query("SELECT c FROM Troupeau c WHERE c.createdBy=:id")
	List<Troupeau> findByCreatedBy(@Param("id") Long id);
	
	boolean existsByName(String name);

}
