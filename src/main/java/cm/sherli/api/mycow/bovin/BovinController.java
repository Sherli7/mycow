package cm.sherli.api.mycow.bovin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
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
import cm.sherli.api.mycow.amenagement.TroupeauRepo;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.log.model.BovinLog;
import cm.sherli.api.mycow.payload.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
@Slf4j
@RequiredArgsConstructor
public class BovinController {

	private final BovinRepo bovinRepo;
	
	private final TroupeauRepo troupRepo;
	private final BovinService bovinService;

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/bovin/{id}")
	public Bovin getBovinId(@PathVariable("id") long id){
		return bovinService.findBybovinid(id);
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/bovin/status/{id}")
	  public ResponseEntity<Bovin> updateBovinStatus(@PathVariable("id") long id, @RequestBody Bovin bovin,@RequestBody BovinLog bovinLog) {
	   return bovinService.updateBovinStatus(id,bovin,bovinLog);
	  }
	
	 @GetMapping("/bovins")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllBovins(Pageable page) {
		 return bovinService.getAllBovins(page);
	  }
	 
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("bovins/{id}")
	public Bovin getBovinById(@PathVariable("id") Long id){
	 return bovinService.getBovinById(id);
	}
	
	 @GetMapping("/bovins/ranch/{ranchid}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	 public List<Bovin> getBovinRanch(@PathVariable("ranchid") Long ranchid) {        
		return bovinService.getBovinByRanch(ranchid);
	  }
	  
	  @GetMapping("/bovins/troupeau/{ranchid}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Bovin> getBovinTroupeau(@PathVariable("ranchid") Long ranchid) {        
		 if(!troupRepo.existsById(ranchid)) {
			 throw new ResourceNotFoundException("This troupeau doesn't exist");
		 }
		return  bovinRepo.findByBovintroupeau(ranchid);
	  }
	  
	  @GetMapping("/bovins/troupeaux/{troupeauid}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Bovin> getBovinTroupeaux(@PathVariable("troupeauid") Long ranchid) {        
		 if(!troupRepo.existsById(ranchid)) {
			 throw new ResourceNotFoundException("This troupeau doesn't exist");
		 }
		   return bovinRepo.findByBovintroupeaux(ranchid);
	  }
	 
	@GetMapping("/bovin")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Bovin> getAllBovin() {
		return bovinRepo.findAll();
	}
	
	  @GetMapping("/bovins/createdBy/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getBovinsCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
				Page<Bovin> pages = bovinRepo.findByCreatedBy(id,page);
				List<Bovin> camp = pages.getContent();
			    Map<String, Object> response = new HashMap<>();
			      response.put("bovinCreator", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/bovin/createdBy/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Bovin> getBovinCreatedBy(@PathVariable(value = "id") Long id) {
		 return bovinRepo.findByCreatedBy(id);
	  }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/bovins")
	public ResponseEntity<?>addBovin(@RequestBody Bovin bovin) {
		if(bovinRepo.existsByuniqueid(bovin.getUniqueid())) {
			throw new ResourceNotFoundException("Error:Uniqueid is already in use");
		}
		bovinRepo.save(bovin);

		return ResponseEntity.ok(new MessageResponse("Bovin recorded successfully!"));
	}
	

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/bovins/{id}")
	  public ResponseEntity<Bovin> updateBovin(@PathVariable("id") long id, @RequestBody Bovin bovin) {
		return bovinService.updateBovin(id, bovin);
	  }



	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/bovins/{id}/restore")
	public ResponseEntity<Bovin> deleteRestoreBovin(@PathVariable("id") long id) {
		    Bovin bov = bovinRepo.findById(id)
			        .orElseThrow(() -> new ResourceNotFoundException("BovinId " + id + " not found"));
		    	bov.setDelete(false);
		    return new ResponseEntity<>(bovinRepo.save(bov), HttpStatus.OK);
	  }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/bovins/{id}/delete")
	public ResponseEntity<Bovin> deleteBovin(@PathVariable("id") long id) {
		    Bovin bov = bovinRepo.findById(id)
			        .orElseThrow(() -> new ResourceNotFoundException("BovinId " + id + " not found"));
		    	bov.setDelete(true);
		    return new ResponseEntity<>(bovinRepo.save(bov), HttpStatus.OK);
	  }
	
	@DeleteMapping("/bovins/deleteAll")
	@PreAuthorize("hasRole('MODERATOR')")
	public void deleteAll() {
		bovinRepo.deleteAll();
	}
	  
	@PreAuthorize("hasRole('MODERATOR')")
	  @GetMapping("/bovins/isdeleted")
	  public ResponseEntity<List<Bovin>> findDeletedBovins() {
	    return bovinService.findDeletedBovins();
	  }

	@PostMapping("/troupeau/bovin/{troupid}/{campid}/{ranchid}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Bovin> createBovins(@PathVariable("ranchid") Long ranchid,
					  @PathVariable("troupid") Long troupid,
					  @PathVariable("campid") Long campid,
			      @RequestBody Bovin bovinRequest) {
		return bovinService.createBovins(ranchid, troupid, campid, bovinRequest);
	}
	
}