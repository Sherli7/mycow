package cm.sherli.api.mycow.group;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Groupes, Long> {

	public  List<Groupes> findByNameContaining(String name);

	Optional<Groupes> findByName(EGroupe name);

}
