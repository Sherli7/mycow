package cm.sherli.api.mycow.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.sherli.api.mycow.log.model.BovinLog;

public interface BovinLogRepo extends JpaRepository<BovinLog, Long>{

	BovinLog findByaction(String action);
}
