package cm.sherli.api.mycow.sante.vaccin;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cm.sherli.api.mycow.bovin.Bovin;

public interface VaccinRepo extends JpaRepository<Vaccin,Long> {
	public Page<Vaccin> findByname(String name,Pageable pagingSort);
	public boolean existsByname(String name);
}
