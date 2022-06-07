package cm.sherli.api.mycow.employee;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.sherli.api.mycow.amenagement.CampementRepository;
import cm.sherli.api.mycow.amenagement.RanchRepository;
import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.amenagement.TroupeauRepo;
import cm.sherli.api.mycow.email.EmailBuilder;
import cm.sherli.api.mycow.email.EmailSender;
import cm.sherli.api.mycow.email.EmailValidator;
import cm.sherli.api.mycow.exception.MessageResponse;
import cm.sherli.api.mycow.exception.ResourceAlreadyExists;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.group.GroupRepository;
import cm.sherli.api.mycow.group.Groupes;
import cm.sherli.api.mycow.group.Role;
import cm.sherli.api.mycow.group.RoleRepository;
import cm.sherli.api.mycow.security.PasswordGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository emplRepository;
	
	@Autowired RoleRepository roleRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	CampementRepository campRepository;
	
	@Autowired
	TroupeauRepo troupeauRepo;
	
	@Autowired
	RanchRepository ranchRepository;
	 @Autowired
	  private PasswordEncoder encoder;
	 
	@Autowired
	TroupeauRepo troupRepository;

	  @Autowired EmailValidator emailValidator;
	  @Autowired EmailSender emailSender;
	  
	  @GetMapping("/employees")
		  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getBovinPaging(Pageable page) {
				Page<Employee> pages = emplRepository.findAll(page);
				List<Employee> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("employees", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  
	  @GetMapping("/employees/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getCampementCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
				Page<Employee> pages = emplRepository.findByCreatedBy(id,page);
				List<Employee> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("employeecreator", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/employee/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public List<Employee> getCampementCreatedBy(@PathVariable(value = "id") Long id) {
		  List<Employee> camp=emplRepository.findByCreatedBy(id);
		  if(camp.isEmpty()) {
			  throw new ResourceNotFoundException("Nothing to show about createdby's id");
		  }
	    return camp;
	  }
	 
	  @GetMapping("/employee")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public List<Employee> getBovin() {
	    return emplRepository.findAll();
	  }
	  
	 @GetMapping("/user")
	  public List<Employee> getAllUsers() {
		 return emplRepository.findByemployee();
	  }
	 
	 @GetMapping("/users")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllUsers(Pageable page) {
				Page<Employee> pages = emplRepository.findByemployees(page);
				List<Employee> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("users", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	 
	 @GetMapping("/user/requestor")
	  public List<Employee> getAllUserRequest() {
		 return emplRepository.findByemployeeRequest();
	  }

	  @GetMapping("/user/requestors")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllRequestors(Pageable page) {
				Page<Employee> pages = emplRepository.findByemployeeRequest(page);
				List<Employee> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("employees", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	 
		 @GetMapping("/user/hasaccount")
		  public ResponseEntity<Map<String, Object>> getUserHasAccount(Pageable page) {
				Page<Employee> pages = emplRepository.findByemployeeHasAccount(page);
				List<Employee> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("employees", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  @PostMapping("/user")
	  public ResponseEntity<?> addEmpl(@RequestBody Employee empl){

		  if(emplRepository.existsByEmail(empl.getEmail())){
					  throw new ResourceAlreadyExists("Error: This email is already in use.",HttpStatus.CONFLICT);
		  }
		  if(emplRepository.existsByCni(empl.getCni())){
			  throw new ResourceAlreadyExists("Error:You cannot use this National Identity Card number",HttpStatus.CONFLICT);
			  }
		  empl.setRequestor(true);
		  emplRepository.save(empl);
		  
		  return ResponseEntity.ok(new MessageResponse("Employee recorded successfully!"));
	  }


	  @GetMapping("/employee/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> getEmplsById(@PathVariable(value = "id") Long id) {
		  Employee employee = emplRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + id));

	    return new ResponseEntity<>(employee, HttpStatus.OK);
	  }
	  
	  
	  @PutMapping("/employee/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> updateEmpl(@PathVariable("id") long id, @RequestBody Employee employeeRequest) {
		  Employee employee = emplRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("employeeId " + id + "not found"));
	    employee.setFirstname(employeeRequest.getFirstname());
	    employee.setLastname(employeeRequest.getLastname());
	    employee.setCni(employeeRequest.getCni());
	    employee.setRanchid(employeeRequest.getRanchid());
	    employee.setCampid(employeeRequest.getCampid());
	    employee.setUpdatedAt(LocalDateTime.now());
	    return new ResponseEntity<>(emplRepository.save(employee), HttpStatus.OK);
	  }
	    
		@PostMapping("/groups/employee/{employId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Groupes> assignEmployeeGroup(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Groupes tagRequest
		  ) {

		    Groupes tag = emplRepository.findById(employId).map(tutorial -> {
		        long tagId = tagRequest.getId();
		        // tag is existed
		        if (tagId != 0L) {
		          Groupes _tag = groupRepository.findById(tagId)
		              .orElseThrow(() -> new ResourceNotFoundException("Not found Groupes with id = " + tagId));
		          tutorial.addGroup(_tag);
		          emplRepository.save(tutorial);
		          return _tag;
		        }
		       
		        // add and create new Tag
		        tutorial.addGroup(tagRequest);
		        return groupRepository.save(tagRequest);
		      }).orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + employId));

		    return new ResponseEntity<>(tag, HttpStatus.CREATED);
		  }
		
		@PostMapping("/role/employee/{employId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Role> assignUserRole(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Role tagRequest
		  ) {

		    Role tag = emplRepository.findById(employId).map(tutorial -> {
		        long tagId = tagRequest.getId();
		        
		        // tag is existed
		        if (tagId != 0L) {
		          Role _tag = roleRepository.findById(tagId)
		              .orElseThrow(() -> new ResourceNotFoundException("Not found Role with id = " + tagId));
		          tutorial.setRequestor(false);
		          tutorial.setActiveAccount(true);
		          tutorial.addRole(_tag);
		          emplRepository.save(tutorial);
		          return _tag;
		        }
		       
		        // add and create new Tag
		        tutorial.addRole(tagRequest);
		        return roleRepository.save(tagRequest);
		      }).orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + employId));

		    return new ResponseEntity<>(tag, HttpStatus.CREATED);
		  }
		
/*
 * ASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: BEGIN		
 */
		
		@PostMapping("/employee/{employId}/troupeau")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<Troupeau> assignEmployeeTroupeau(@PathVariable(value = "employId") Long employId, 
				  @RequestBody Troupeau troupeauRequest	  ) {
			Troupeau tag = emplRepository.findById(employId).map(troup -> {
		        long tagId = troupeauRequest.getId();
		        // tag is existed
		        if (tagId != 0L) {
		        	Troupeau _tag = troupRepository.findById(tagId)
		              .orElseThrow(() -> new ResourceNotFoundException("Not found Troupeau with id = " + tagId));
		        	troup.addTroupeau(_tag);
		          emplRepository.save(troup);
		          return _tag;
		        }
		       
		        // add and create new Tag
		        troup.addTroupeau(troupeauRequest);
		        return troupRepository.save(troupeauRequest);
		      }).orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + employId));

		    return new ResponseEntity<>(tag, HttpStatus.CREATED);
		  }

	/*
	 *  ASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: END	
	 */
		
		 @DeleteMapping("/role/employee/{employId}/{roleId}")
		  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
		  public ResponseEntity<HttpStatus> deleteEmployeeFromRole(@PathVariable(value = "employId") Long employId, @PathVariable(value = "roleId") Long groupId) {
		    Employee employ = emplRepository.findById(employId)
		        .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
		    employ.removeRole(groupId);
		    emplRepository.save(employ);
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  }
 	 /*
 	  * DISASSIGN EMPLOYEE TO RANCH,TO CAMPEMENT,TO TROUPEAU: BEGIN			
 	  */
 	  
 	 @DeleteMapping("/employee/{employId}/troupeau/{troupId}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<HttpStatus> deleteTroupeauFromEmployee(@PathVariable(value = "employId") Long employId, @PathVariable(value = "troupId") Long troupId) {
	    Employee employee = emplRepository.findById(employId)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
	    employee.removeTroupeau(troupId);
	    emplRepository.save(employee);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
 	 
 	  @DeleteMapping("/groups/employee/{employId}/{groupId}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<HttpStatus> deleteEmployeeFromGroup(@PathVariable(value = "employId") Long employId, @PathVariable(value = "groupId") Long groupId) {
	    Employee employ = emplRepository.findById(employId)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
	    employ.removeGroup(groupId);
	    emplRepository.save(employ);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @PutMapping("/activate/user/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<String> activate(@PathVariable("id") long id) {
		  Employee employee = emplRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "not found"));
		  
		  if(!ranchRepository.existsById(employee.getRanchid())) {
	    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("This ranch is not found already exists");
	    	}
		  
		  if(!campRepository.existsById(employee.getCampid())) {
		    		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("This campid is not found");
		   }
		  
		  employee.setRequestor(false);
		  employee.setActiveAccount(true);
	    employee.setUpdatedAt(LocalDateTime.now());
	    emplRepository.save(employee);
	    return ResponseEntity.status(HttpStatus.OK).build();
	  }
	  
	  @PutMapping("/employee/lock/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Employee> LockAnddisactivate(@PathVariable("id") long id,@RequestBody Employee empl) {
		  Employee employee = emplRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "not found"));
		 
		  if(id==0) {
			  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		  }
			employee.setLockedAccount(empl.isLockedAccount());
	    employee.setUpdatedAt(LocalDateTime.now());
	    return new ResponseEntity<>(emplRepository.save(employee), HttpStatus.OK);
	  }
	  
	  @PostMapping("/employee")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<String> createEmployee(@RequestBody Employee employeeRequest) {
			  
			  if(emplRepository.existsByCni(employeeRequest.getCni())) {
		    		return  ResponseEntity.status(HttpStatus.CONFLICT).body("This object already exists");
		    }
			 employeeRequest.setActiveAccount(false);
			 employeeRequest.setRequestor(false);
			 employeeRequest.setLockedAccount(true);
			 employeeRequest.setFirstname(employeeRequest.getFirstname().toLowerCase());
			 employeeRequest.setLastname(employeeRequest.getLastname().toLowerCase());
	     emplRepository.save(employeeRequest);
	     return ResponseEntity.status(HttpStatus.CREATED).build();
	  }
	  
	  @DeleteMapping("/employee/{ranchid}")
	  @PreAuthorize("hasRole('MODERATOR') ")
	  public ResponseEntity<HttpStatus> deleteAllRanchs(@PathVariable("ranchid")Long id) {
		  emplRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  	  
	  
}
