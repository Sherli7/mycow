package cm.sherli.api.mycow.amenagement;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TroupeauRepo extends JpaRepository<Troupeau, Long> {
	@Query("SELECT c FROM Troupeau c WHERE c.createdBy =:id")
	Page<Troupeau> findByCreatedBy(@Param("id") Long id, Pageable page);
	@Query("SELECT c FROM Troupeau c WHERE c.createdBy=:id")
	List<Troupeau> findByCreatedBy(@Param("id") Long id);
	boolean existsByName(String name);
}
