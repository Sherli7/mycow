package cm.sherli.api.mycow.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cm.sherli.api.mycow.log.model.VaccinLog;

public interface VaccinLogRepo extends JpaRepository<VaccinLog, Long> {

}
