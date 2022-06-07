package cm.sherli.api.mycow.log.services;

import java.util.List;
import java.util.Optional;

import cm.sherli.api.mycow.log.model.BovinLog;


public interface BovinLogService {
	BovinLog saveBovinLog(BovinLog bovinLog);
	BovinLog getBovinLogByAction(String action);
	List<BovinLog>getAllBovinLog();
	Optional<BovinLog> getBovinLogByBovinId(Long id);
}
