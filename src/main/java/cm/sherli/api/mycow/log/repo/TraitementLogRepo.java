package cm.sherli.api.mycow.log.repo;

import cm.sherli.api.mycow.log.model.TraitementLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TraitementLogRepo extends JpaRepository<TraitementLog,Long> {


    @Query(value="SELECT * FROM traitement_log WHERE bovinid=id",nativeQuery=true)
    Page<TraitementLog> findBybovinid(Long id, Pageable page);

}
