package cm.sherli.api.mycow.amenagement;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RanchRepository extends JpaRepository<Ranch, Long>{

	Page<Ranch> findByNameContaining(String title, Pageable pagingSort);
	
	@Query(value="SELECT c.name FROM Ranch c WHERE c.id =:id")
	String findByName(@Param("id") Long id);
	List<Ranch> findByName(String name, Sort sort);
boolean existsByname(String name);
	List<Ranch> findAllByName(String name, Pageable pageable);
	
	@Query("SELECT c FROM Ranch c WHERE c.createdBy =:id")
	Page<Ranch> findByCreatedBy(@Param("id") Long id, Pageable firstPageWithTwoElements);
	
	@Query("SELECT c FROM Ranch c WHERE c.createdBy =:id")
	List<Ranch> findByCreatedBy(@Param("id") Long id);
}
