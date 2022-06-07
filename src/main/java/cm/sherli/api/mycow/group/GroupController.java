package cm.sherli.api.mycow.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cm.sherli.api.mycow.employee.EmployeeRepository;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
public class GroupController {
	
	 @Autowired
	  GroupRepo gRepository;
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  @GetMapping("/group")
	  public ResponseEntity<List<Groupes>> getAllgroups(@RequestParam(required = false) String name) {
	    List<Groupes> groupes = new ArrayList<Groupes>();

	    if (name == null)
	      gRepository.findAll().forEach(groupes::add);
	    else
	      gRepository.findByNameContaining(name).forEach(groupes::add);

	    if (groupes.isEmpty()) {
	    	 throw new ResourceNotFoundException("No group found");
	    }

	    return new ResponseEntity<>(groupes, HttpStatus.OK);
	  }
	  
	  @GetMapping("/groups")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN' or hasRole('USER'))")
	  public ResponseEntity<Map<String, Object>> getAllPaginatingUsers(Pageable page) {
		  Page<Groupes> pages = gRepository.findAll(page);
		  List<Groupes> troupeau = pages.getContent();
		  Map<String, Object> response = new HashMap<>();
	      response.put("groups", troupeau);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalGroups", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	  }

	  @GetMapping("/groups/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')or hasRole('USER')")
	  public ResponseEntity<Groupes> getGroupById(@PathVariable("id") long id) {
	    Groupes Groupes = gRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Groupes with id = " + id));

	    return new ResponseEntity<>(Groupes, HttpStatus.OK);
	  }

	  @PostMapping("/groups")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	  public ResponseEntity<Groupes> createGroup(@RequestBody Groupes Groupes) {
	    Groupes _Group = gRepository.save(new Groupes(Groupes.getName(), Groupes.getDescription()));
	    return new ResponseEntity<>(_Group, HttpStatus.CREATED);
	  }

	  @PutMapping("/groups/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	  public ResponseEntity<Groupes> updateGroup(@PathVariable("id") long id, @RequestBody Groupes Groupes) {
	    Groupes _Group = gRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Groupes with id = " + id));

	    _Group.setName(Groupes.getName());
	    _Group.setDescription(Groupes.getDescription());
	    
	    return new ResponseEntity<>(gRepository.save(_Group), HttpStatus.OK);
	  }

	  @DeleteMapping("/groups/{id}")
	  public ResponseEntity<HttpStatus> deleteGroup(@PathVariable("id") long id) {
	    gRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @DeleteMapping("/groups")
	  public ResponseEntity<HttpStatus> deleteAllgroups() {
	    gRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
}