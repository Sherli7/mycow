package cm.sherli.api.mycow.localisation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    List<Department> findByRegionId(int id);
}
