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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cm.sherli.api.mycow.exception.BadRequestException;
import cm.sherli.api.mycow.exception.ResourceAlreadyExists;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/api/mycow")
public class RanchController {

	@Autowired
	RanchRepository ranchRepository;
	
	 @GetMapping("/ranchs")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('USER') or hasRole('ADMIN')")
	  public ResponseEntity<Map<String, Object>> getAllRanchs(Pageable page) {
			Page<Ranch> pages = ranchRepository.findAll(page);

			List<Ranch> ranch = pages.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("ranchs", ranch);
		      response.put("currentPage", pages.getNumber());
		      response.put("totalRanch", pages.getTotalElements());
		      response.put("totalPages", pages.getTotalPages());
		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	 
	 
	 @GetMapping("/ranch")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	  public List<Ranch> getAllRanch() {
		 return ranchRepository.findAll();
	  }

	 @GetMapping("/ranchs/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	  public ResponseEntity<Map<String, Object>> getRanchCreatedByPaging(@PathVariable(value = "id") Long id) {
			 Pageable firstPageWithTwoElements = PageRequest.of(0,3);
				Page<Ranch> pages = ranchRepository.findByCreatedBy(id,firstPageWithTwoElements);
				List<Ranch> camp = pages.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("ranchs", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/ranch/createdBy/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	  public List<Ranch> getCampementCreatedBy(@PathVariable(value = "id") Long id) {
		  List<Ranch> camp=ranchRepository.findByCreatedBy(id);
		  if(camp.isEmpty()) {
			  throw new ResourceNotFoundException("Empty list");
		  }
	    return camp;
	  }
	
	  @GetMapping("/ranch/{id}")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Ranch> getRanchById(@PathVariable("id") long id) {
	    Ranch ranch = ranchRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Ranch with id = " + id));

	    return new ResponseEntity<>(ranch, HttpStatus.OK);
	  }

	  @PostMapping("/ranch")
	  @PreAuthorize("hasRole('MODERATOR')")
	  public ResponseEntity<Ranch> createRanch(@RequestBody Ranch ranch) {
		  if(ranchRepository.existsByname(ranch.getName())) {
			  throw new ResourceAlreadyExists("This ranch's name is already exist",HttpStatus.CONFLICT);
		  }
		  if(ranch.getLatitude().isBlank()) {
			  throw new BadRequestException("Please check latitude field",HttpStatus.BAD_REQUEST);
		  }
		  if(ranch.getLongitude().isBlank()) {
			  throw new BadRequestException("Please check Longitude field",HttpStatus.BAD_REQUEST);
		  }
		Ranch r= ranchRepository.save(ranch);
	    return new ResponseEntity<>(r, HttpStatus.CREATED);
	  }

	  @PutMapping("/ranch/{id}")
      @PreAuthorize("hasRole('MODERATOR')")
	  public ResponseEntity<Ranch> updateRanch(@PathVariable("id") long id, @RequestBody Ranch ranch) {
	    Ranch _ranch= ranchRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Ranch with id = " + id));
	     _ranch.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC+1")));
	     _ranch.setLatitude(ranch.getLatitude());
	     _ranch.setLongitude(ranch.getLongitude());
	    _ranch.setName(ranch.getName());
	    _ranch.setArea(ranch.getArea());
	    
	    return new ResponseEntity<>(ranchRepository.save(_ranch), HttpStatus.OK);
	  }

	  @DeleteMapping("/ranch/{id}")
	  @PreAuthorize("hasRole('MODERATOR')")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		  ranchRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @DeleteMapping("/ranch")
	  @PreAuthorize("hasRole('MODERATOR') ")
	  public ResponseEntity<HttpStatus> deleteAllRanchs() {
		  ranchRepository.deleteAll();
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	
}
