package cm.sherli.api.mycow.employee;

import cm.sherli.api.mycow.amenagement.CampementRepository;
import cm.sherli.api.mycow.amenagement.RanchRepository;
import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.amenagement.TroupeauRepo;
import cm.sherli.api.mycow.exception.MessageResponse;
import cm.sherli.api.mycow.exception.ResourceAlreadyExists;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.group.GroupRepository;
import cm.sherli.api.mycow.group.Groupes;
import cm.sherli.api.mycow.group.Role;
import cm.sherli.api.mycow.group.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final  EmployeeRepository emplRepository;
    private final  RoleRepository roleRepository;
    private final  GroupRepository groupRepository;
    private final  CampementRepository campRepository;
    private final   RanchRepository ranchRepository;
    private final   TroupeauRepo troupRepository;

    public List<Employee> getEmployee() {
        return emplRepository.findAll();
    }
    public List<Employee> getAllUsers() {
        return emplRepository.findByemployee();
    }

    public List<Employee> getAllUserRequest() {
        return emplRepository.findByemployeeRequest();
    }

    public ResponseEntity<Map<String, Object>> getEmployeeCreatedByPaging( Long id, Pageable page) {
        Page<Employee> pages = emplRepository.findByCreatedBy(id,page);
        List<Employee> camp = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("employeecreator", camp);
        response.put("currentPage", pages.getNumber());
        response.put("totalCreated", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getEmployeePaging(Pageable page) {
        Page<Employee> pages = emplRepository.findAll(page);
        List<Employee> employ = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("employees", employ);
        response.put("currentPage", pages.getNumber());
        response.put("totalCreated", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public List<Employee> getEmployeeCreatedBy(Long id) {
        List<Employee> empl=emplRepository.findByCreatedBy(id);
        if(empl.isEmpty()) {
            throw new ResourceNotFoundException("Nothing to show about createdby's id");
        }
        return empl;
    }

    public ResponseEntity<Map<String, Object>> getAllUsers(Pageable page) {
        Page<Employee> pages = emplRepository.findByemployees(page);
        List<Employee> employee = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("users", employee);
        response.put("currentPage", pages.getNumber());
        response.put("totalCreated", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getAllUsersRequestors(Pageable page) {
        Page<Employee> pages = emplRepository.findByemployeeRequest(page);
        List<Employee> employee = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("employees", employee);
        response.put("currentPage", pages.getNumber());
        response.put("totalCreated", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getUserHasAccount(Pageable page) {
        Page<Employee> pages = emplRepository.findByemployeeHasAccount(page);
        List<Employee> empl = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("employees", empl);
        response.put("currentPage", pages.getNumber());
        response.put("totalCreated", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<?> addEmpl(Employee empl){
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

    public ResponseEntity<Employee> getEmplById(Long id) {
        Employee employee = emplRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found employee with id = " + id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    public ResponseEntity<Employee> updateEmpl(long id, Employee employeeRequest) {
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

    public ResponseEntity<Groupes> assignEmployeeGroup(Long employId, Groupes tagRequest ) {
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
    public ResponseEntity<Role> assignUserRole(Long employId,Role tagRequest) {
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

    public ResponseEntity<Troupeau> assignEmployeeTroupeau(Long employId,Troupeau troupeauRequest	  ) {
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

    public ResponseEntity<HttpStatus> deleteEmployeeFromRole(Long employId,Long roleId) {
        Employee employ = emplRepository.findById(employId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
        employ.removeRole(roleId);
        emplRepository.save(employ);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<HttpStatus> deleteTroupeauFromEmployee(Long employId,  Long troupId) {
        Employee employee = emplRepository.findById(employId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
        employee.removeTroupeau(troupId);
        emplRepository.save(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<HttpStatus> deleteEmployeeFromGroup(Long employId, Long groupId) {
        Employee employ = emplRepository.findById(employId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Employee with id = " + employId));
        employ.removeGroup(groupId);
        emplRepository.save(employ);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> activate(long id) {
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

    public ResponseEntity<Employee> LockAnddisactivate(long id,Employee empl) {
        Employee employee = emplRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + "not found"));
        if(id==0) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        employee.setLockedAccount(empl.isLockedAccount());
        employee.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(emplRepository.save(employee), HttpStatus.OK);
    }

    public ResponseEntity<String> createEmployee(Employee employeeRequest) {
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

    public void deleteEmployeeById(Long id) {
        emplRepository.deleteById(id);
    }
}