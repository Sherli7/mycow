package cm.sherli.api.mycow.amenagement;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.sherli.api.mycow.exception.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
public class CampementController {

	@Autowired
	RanchRepository ranchRepository;
	
	@Autowired 
	CampementRepository campementRepository;
	
	 @GetMapping("/campements")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllCampements(Pageable page) {
			Page<Campement> pages = campementRepository.findAll(page);

			List<Campement> ranch = pages.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("campements", ranch);
		      response.put("currentPage", pages.getNumber());
		      response.put("totalCampement", pages.getTotalElements());
		      response.put("totalPages", pages.getTotalPages());
		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	 
	 @GetMapping("/campement")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Campement> getAllCampement() {
		 return campementRepository.findAll();
	  }

	  @GetMapping("/campement/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public ResponseEntity<Campement> getCampementByRanchId(@PathVariable(value = "id") Long id) {
	    Campement campement = campementRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Campement with id = " + id));

	    return new ResponseEntity<>(campement, HttpStatus.OK);
	  }

	  @GetMapping("/campements/createdBy/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getCampementCreatedByPaging(@PathVariable(value = "id") Long id) {
			 Pageable firstPageWithTwoElements = PageRequest.of(0,3);
				Page<Campement> pages = campementRepository.findByCreatedBy(id,firstPageWithTwoElements);
				List<Campement> camp = pages.getContent();

			      Map<String, Object> response = new HashMap<>();
			      response.put("campements", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/campement/createdBy/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Campement> getCampementCreatedBy(@PathVariable(value = "id") Long id) {
		  List<Campement> camp=campementRepository.findByCreatedBy(id);
		  if(camp.isEmpty()) {
			  throw new ResourceNotFoundException("Empty list");
		  }
	    return camp;
	  }
	  
	  
	  @PostMapping("/campement")
	  @PreAuthorize("hasRole('ADMIN')")
	  public ResponseEntity<Campement> createCampement(@RequestBody Campement campementRequest) {
	    return new ResponseEntity<>(campementRepository.save(campementRequest), HttpStatus.CREATED);
	  }

	  @PutMapping("/campement/{id}")
	  @PreAuthorize("hasRole('ADMIN')")
	  public ResponseEntity<Campement> updateCampement(@PathVariable("id") long id, @RequestBody Campement camp) {
	    Campement _campement= campementRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found campement with id = " + id));
	    
	     _campement.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
	    _campement.setName(camp.getName());
	    _campement.setLatitude(camp.getLatitude());
	    _campement.setLongitude(camp.getLongitude());
	    _campement.setArea(camp.getArea());
	    return new ResponseEntity<>(campementRepository.save(_campement), HttpStatus.OK);
	  }


	 /*
	  *  Suppression  campement
	  *
	  @DeleteMapping("/campement/{id}")
	  public ResponseEntity<HttpStatus> deleteCampement(@PathVariable("id") long id) {
	    campementRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  *
	  */
	
}
