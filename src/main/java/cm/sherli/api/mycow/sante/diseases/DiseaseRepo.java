package cm.sherli.api.mycow.sante.diseases;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepo extends JpaRepository<Disease, Long> {
	Disease findByid(Long id);
	public List<Disease> findByname(String libelle);
	boolean existsByname(String libelle);
}
