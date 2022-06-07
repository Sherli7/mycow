package cm.sherli.api.mycow.state;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cm.sherli.api.mycow.bovin.Bovin;

@Repository
public interface EstMaladeRepo extends JpaRepository<EstMalade, Long>{


	  @Query(value="SELECT * FROM est_malade  where  bovinid=:id",nativeQuery=true)
		 List<EstMalade> findByBovinMalade(@Param("id") Long id);
}
