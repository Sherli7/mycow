package cm.sherli.api.mycow.bovin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BovinRepo extends JpaRepository<Bovin, Long>{
	//public List<Bovin> findByuniqueid(String uniqueid);
	
	@Query(value="SELECT * FROM bovins WHERE sex like '%M'",nativeQuery=true)
	List<Bovin> findBysex(String sex);
	Boolean existsByuniqueid(String uniqueid);
	Bovin findBybovinid(Long bovinid);
	List<Bovin> findByIsDelete(boolean isDelete);
	/*
	*Page<Bovin> findByUniqueid(String title, Pageable pagingSort);
	*/
	@Query("SELECT c FROM Bovin c WHERE c.createdBy =:id")
	//Page<Bovin> findByCreatedBy(@Param("id") Long id, int page, int size);
	Page<Bovin> findByCreatedBy(Long id, Pageable firstPageWithTwoElements);
	
	@Query("SELECT c FROM Bovin c WHERE c.createdBy =:id")
	//Page<Bovin> findByCreatedBy(@Param("id") Long id, int page, int size);
	List<Bovin> findByCreatedBy(Long id);
	
	  @Query(value="SELECT p.* FROM bovins p LEFT JOIN troupeau u on u.id=p.troupeauid WHERE u.ranchid=:id ",nativeQuery=true
		     )
	 List<Bovin> findByBovin(@Param("id") Long id);
	  
	  @Query(value="SELECT b.bovinid FROM bovins b,troupeau t,campements c where  b.troupeauid=t.id and t.campid=c.id and b.troupeauid=:id",nativeQuery=true)
	 List<Bovin> findByBovintroupeau(@Param("id") Long id);

	  @Query(value="SELECT b.bovinid FROM bovins b where b.troupeauid=:id",nativeQuery=true)
	 List<Bovin> findByBovintroupeaux(@Param("id") Long id);
		
}
