package cm.sherli.api.mycow.log.repo;

import cm.sherli.api.mycow.log.model.TraitementLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TraitementLogRepo extends JpaRepository<TraitementLog,Long> {


    @Query("SELECT t FROM TraitementLog t WHERE t.bovinid=:id")
    Page<TraitementLog> findBybovinid(Long id, Pageable page);

}
