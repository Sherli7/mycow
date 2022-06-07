package cm.sherli.api.mycow.amenagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import cm.sherli.api.mycow.bovin.Bovin;
import cm.sherli.api.mycow.bovin.BovinRepo;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
public class TroupeauController {

  @Autowired
  TroupeauRepo troupRepo;

  @Autowired
  BovinRepo bovinRepo;
  
  @Autowired
  CampementRepository campementRepository;

@GetMapping("/troupeau")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
public List<Troupeau> getAllTroupeauxWithoutPaging()
{
   return troupRepo.findAll();
}
 
  @GetMapping("/troupeaux")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>> getAllTroupeaux(Pageable page) {
	  Page<Troupeau> pages = troupRepo.findAll(page);
	  List<Troupeau> troupeau = pages.getContent();
	  Map<String, Object> response = new HashMap<>();
      response.put("troupeaux", troupeau);
      response.put("currentPage", pages.getNumber());
      response.put("totalTroupeau", pages.getTotalElements());
      response.put("totalPages", pages.getTotalPages());
      return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @GetMapping("/troupeaux/createdBy/{id}")
  public ResponseEntity<Map<String, Object>> getCampementCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
			Page<Troupeau> pages = troupRepo.findByCreatedBy(id,page);
			List<Troupeau> camp = pages.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("troupeaux", camp);
		      response.put("currentPage", pages.getNumber());
		      response.put("totalCreated", pages.getTotalElements());
		      response.put("totalPages", pages.getTotalPages());
		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
  }
  
  @GetMapping("/troupeau/createdBy/{id}")
  public List<Troupeau> getTroupeauCreatedBy(@PathVariable(value = "id") Long id) {
	  List<Troupeau> camp=troupRepo.findByCreatedBy(id);
    return camp;
  }
  
  
  @GetMapping("/troupeau/{id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Troupeau> getTroupeauById(@PathVariable("id") Long id) {
	  Troupeau troupeau = troupRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Troupeau with id = " + id));

    return new ResponseEntity<>(troupeau, HttpStatus.OK);
  }

  @PutMapping("/troupeau/{id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Troupeau> updateTroupeau(@PathVariable("id") long id, @RequestBody Troupeau tagRequest) {
	  Troupeau troups = troupRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Not found Bovin with id = " + id));
			  troups.setName(tagRequest.getName());
			  troups.setDescription(tagRequest.getDescription());
		    return new ResponseEntity<>(troupRepo.save(troups), HttpStatus.OK);
  }
  
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @DeleteMapping("/troupeau/{id}")
  public ResponseEntity<HttpStatus> deleteTroupeau(@PathVariable("id") long id) {
    troupRepo.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  

  @PostMapping("/troupeau")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Troupeau> createTroupeau(@RequestBody Troupeau troupeauRequest) {
	  if(troupRepo.existsByName(troupeauRequest.getName())) {
		  return new ResponseEntity<>(HttpStatus.CONFLICT);
	  }
    return new ResponseEntity<>(troupRepo.save(troupeauRequest), HttpStatus.CREATED);
  }
  
  
}
