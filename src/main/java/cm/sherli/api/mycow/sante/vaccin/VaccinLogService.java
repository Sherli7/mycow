package cm.sherli.api.mycow.sante.vaccin;

import java.util.List;
import java.util.Optional;

import cm.sherli.api.mycow.log.model.VaccinLog;

public interface VaccinLogService {
		VaccinLog saveVaccinLog(VaccinLog bovinLog);
		VaccinLog getVaccinLogByAction(String action);
		List<VaccinLog>getAllVaccinLog();
		Optional<VaccinLog> getVaccinLogByVaccinId(Long id);
	
}
