package cm.sherli.api.mycow.employee;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.group.Groupes;
import cm.sherli.api.mycow.group.Role;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mycow")
public class EmployeeController {
	private final EmployeeService employeeService;
	  @GetMapping("/employees")
		  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getEmployeesPaging(Pageable page) {
				return employeeService.getEmployeePaging(page);
	  }
	  
	  
	  @GetMapping("/employees/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getEmployeeCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
				return employeeService.getEmployeeCreatedByPaging(id,page);
	  }
	  
	  @GetMapping("/employee/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public List<Employee> getEmployeeCreatedBy(@PathVariable(value = "id") Long id) {
		return employeeService.getEmployeeCreatedBy(id);
	  }
	 
	  @GetMapping("/employee")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public List<Employee> getEmployee() {
	    return employeeService.getEmployee();
	  }
	  
	 @GetMapping("/user")
	  public List<Employee> getAllUsers() {
		 return employeeService.getAllUsers();
	  }
	 
	 @GetMapping("/users")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllUsers(Pageable page) {
				return employeeService.getAllUsers(page);
	  }
	 @GetMapping("/user/requestor")
	  public List<Employee> getAllUserRequest() {
		 return employeeService.getAllUserRequest();
	  }

	  @GetMapping("/user/requestors")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllRequestors(Pageable page) {
				return employeeService.getAllUsersRequestors(page);
	  }
	 
		 @GetMapping("/user/hasaccount")
		  public ResponseEntity<Map<String, Object>> getUserHasAccount(Pageable page) {
				return employeeService.getUserHasAccount(page);
	  }
	  
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  @PostMapping("/user")
	  public ResponseEntity<?> addEmpl(@RequestBody Employee empl){
		return employeeService.addEmpl(empl);
	  }

	  @GetMapping("/employee/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> getEmplsById(@PathVariable(value = "id") Long id) {
	    return employeeService.getEmplById(id);
	  }
	  
	  
	  @PutMapping("/employee/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> updateEmpl(@PathVariable("id") long id, @RequestBody Employee employeeRequest) {
		 return employeeService.updateEmpl(id,employeeRequest);
	  }
	    
		@PostMapping("/groups/employee/{employId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Groupes> assignEmployeeGroup(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Groupes tagRequest) {
			return employeeService.assignEmployeeGroup(employId,tagRequest);
		  }
		
		@PostMapping("/role/employee/{employId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Role> assignUserRole(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Role tagRequest) {
				return employeeService.assignUserRole(employId,tagRequest);
		  }
		
/*
 * ASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: BEGIN		
 */
		
		@PostMapping("/employee/troupeau/{employId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Troupeau> assignEmployeeTroupeau(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Troupeau troupeauRequest	  ) {
			return employeeService.assignEmployeeTroupeau(employId,troupeauRequest);
		  }

	/*
	 *  ASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: END	
	 */
		
		 @DeleteMapping("/role/employee/{employId}/{roleid}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<HttpStatus> deleteEmployeeFromRole(@PathVariable(value = "employId") Long employId, @PathVariable(value = "roleid") Long roleid) {
		  return employeeService.deleteEmployeeFromRole(roleid,employId);
		  }
 	 /*
 	  * DISASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: BEGIN			
 	  */
 	  
 	 @DeleteMapping("/employee/{employId}/troupeau/{troupId}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<HttpStatus> deleteTroupeauFromEmployee(@PathVariable(value = "employId") Long employId, @PathVariable(value = "troupId") Long troupId) {
	  return employeeService.deleteTroupeauFromEmployee(employId,troupId);
	  }
 	 
 	  @DeleteMapping("/groups/employee/{employId}/{groupId}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<HttpStatus> deleteEmployeeFromGroup(@PathVariable(value = "employId") Long employId, @PathVariable(value = "groupId") Long groupId) {
	    return employeeService.deleteEmployeeFromGroup(employId,groupId);
	  }

	  @PutMapping("/activate/user/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<String> activate(@PathVariable("id") long id) {
		return employeeService.activate(id);
	  }
	  
	  @PutMapping("/employee/lock/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> LockAnddisactivate(@PathVariable("id") long id,@RequestBody Employee empl) {
		return employeeService.LockAnddisactivate(id,empl);
	  }

	  @PostMapping("/employee")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<String> createEmployee(@RequestBody Employee employeeRequest) {
		return employeeService.createEmployee(employeeRequest);
	  }
	  @DeleteMapping("/employee/{id}")
	  @PreAuthorize("hasRole('MODERATOR') ")
	  public void deleteEmployeeById(@PathVariable("id")Long id) {
		employeeService.deleteEmployeeById(id);
	  }
	  
}
