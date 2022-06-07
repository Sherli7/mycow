package cm.sherli.api.mycow.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByEmail(String email, Pageable pagingSort);
    List<Employee> findBylastname(String lastname);
    List<Employee> findByfirstname(String firstname);
    List<Employee> findBycni(String lastname);
	@Query(value="select e from Employee e WHERE e.activeAccount=true")
	Page<Employee> findByemployees(Pageable firstPageWithTwoElements);

	@Query(value="select e from Employee e WHERE e.requestor != null")
	List<Employee> findByemployee();
	
	@Query(value="select e from Employee e WHERE e.requestor = true")
	List<Employee> findByemployeeRequest();
	

	@Query(value="select e from Employee e WHERE e.requestor = true")
	Page<Employee> findByemployeeRequest(Pageable page);
	
	@Query(value="select roles.user_id as user_id, roles.role_id as role_id, role1_.id as employ_id,role1_.id as roleId, role1_.name as role_name from user_roles roles inner join roles role1_ on roles.role_id=role1_.id where roles.user_id=:id",nativeQuery=true)
	List<Employee> findByRole(@Param("id") Long id);
	
	@Query("SELECT c FROM Employee c WHERE c.createdBy =:id")
	Page<Employee> findByCreatedBy(@Param("id") Long id, Pageable firstPageWithTwoElements);
	
	@Query("SELECT c FROM Employee c WHERE c.createdBy =:id")
	List<Employee> findByCreatedBy(@Param("id") Long id);
	

	@Query(value="select e from Employee e WHERE e.activeAccount=true")
	Page<Employee> findByemployeeHasAccount(Pageable page);
	

	@Query("SELECT c FROM Employee c WHERE c.id =?1")
	Optional<Employee> findByPersonnel(Long id);
	
    Optional<Employee> findByEmail(String email);
    
    
    Boolean existsByEmail(String email);
    Boolean existsByFirstname(String firstname);
    Boolean existsByLastname(String lastname);
    Boolean existsByCni(String cni);
    Page<Employee> findByRoles(Long id,Pageable pagingSort);
    
    
    
}