package cm.sherli.api.mycow.sante.traitements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cm.sherli.api.mycow.log.model.TraitementLog;
import cm.sherli.api.mycow.log.services.TraitementLogImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

import cm.sherli.api.mycow.bovin.BovinRepo;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.payload.response.MessageResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mycow")
@RequiredArgsConstructor
@Slf4j
public class TraitementController {

	private final TraitementRepo traitementRepo;
	private final BovinRepo bovinRepo;

	private final TraitementLogImpl traitementLogService;
	
	
	 @GetMapping("/traitements/createdBy/{id}")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Map<String, Object>> getCampementCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
				Page<Traitement> pages = traitementRepo.findByCreatedBy(id,page);
				List<Traitement> camp = pages.getContent();

			      Map<String, Object> response = new HashMap<>();
			      response.put("traitements", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/traitement/createdBy/{id}")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public List<Traitement> getCampementCreatedBy(@PathVariable(value = "id") Long id) {
		  List<Traitement> insem=traitementRepo.findByCreatedBy(id);
		  if(insem.isEmpty()) {
			  throw new ResourceNotFoundException("Empty list");
		  }
	    return insem;
	  }

	@GetMapping("/traitements")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<Map<String, Object>> getAllTraitements(Pageable page) {
			Page<Traitement> pages = traitementRepo.findAll(page);

			List<Traitement> trait = pages.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("traitements", trait);
		      response.put("currentPage", pages.getNumber());
		      response.put("totalTraitement", pages.getTotalElements());
		      response.put("totalPages", pages.getTotalPages());
		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
	}

		 @GetMapping("/traitement")
		 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
		  public List<Traitement> getAllDisease() {
			 return traitementRepo.findAll();
		  }
	
	@PostMapping("/traitement")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?>addTraitement(@RequestBody Traitement trait) {
		if(traitementRepo.existsByLibelle(trait.getLibelle())) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		traitementRepo.save(trait);
		return ResponseEntity.ok(new MessageResponse("Treatment recorded successfully!"));
	}
	
	
	@GetMapping("/traitement/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<Traitement> getTraitementById(@PathVariable("id") Long id){
		Traitement trait=traitementRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No found treatment with id "+ id));	
	  return new ResponseEntity<>(trait,HttpStatus.OK);
	}
	
	@PutMapping("/traitement/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Traitement> updateTraitement(@PathVariable("id") long id, @RequestBody Traitement traitement) {
		Traitement trait = traitementRepo.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Traitement with " + id + "not found"));
		trait.setDescription(traitement.getDescription());
		trait.setLibelle(traitement.getLibelle());
	    return new ResponseEntity<>(traitementRepo.save(trait), HttpStatus.OK);
	  }
	
	@PostMapping("/bovin/traitement/{bovinid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Traitement> assignBovinTraitement(@PathVariable(value = "bovinid") Long bovinid, 
			  @RequestBody Traitement traitementRequest	  ) {
		Traitement tag = bovinRepo.findById(bovinid).map(troup -> {
	        long tagId = traitementRequest.getId();
	        // tag is existed
	        if (tagId != 0L) {
	        	Traitement _tag = traitementRepo.findById(tagId)
	              .orElseThrow(() -> new ResourceNotFoundException("Not found Treatement with id = " + tagId));
	        	troup.addTraitement(_tag);
	          bovinRepo.save(troup);
	          return _tag;
	        }
	        // add and create new Tag
	        troup.addTraitement(traitementRequest);
	        return traitementRepo.save(traitementRequest);
	      }).orElseThrow(() -> new ResourceNotFoundException("Not found Bovin with id = " + bovinid));
		LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("UTC"));
		Traitement name=traitementRepo.findById(traitementRequest.getId()).orElseThrow(()->new ResourceNotFoundException("Treatment "+traitementRequest.getId()+" not found"));
		log.info(name.getLibelle());
		traitementLogService.traitIssue(new TraitementLog(null,bovinid,name.getLibelle(),"A déternimer",createdAt.toLocalDate().toString()));
	    return new ResponseEntity<>(tag,HttpStatus.CREATED);
	  }

	
}
