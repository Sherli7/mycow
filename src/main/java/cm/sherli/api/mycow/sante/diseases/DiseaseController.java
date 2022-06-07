package cm.sherli.api.mycow.sante.diseases;

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

import cm.sherli.api.mycow.bovin.Bovin;
import cm.sherli.api.mycow.bovin.BovinRepo;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.payload.response.MessageResponse;
import cm.sherli.api.mycow.state.EstMalade;
import cm.sherli.api.mycow.state.EstMaladeRepo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mycow/")
public class DiseaseController {

@Autowired DiseaseRepo diseaseRepo;
@Autowired EstMaladeRepo estMaladeRepo;
@Autowired BovinRepo bovinRepo;
	
	
@GetMapping("/diseases")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
public ResponseEntity<Map<String, Object>> getAllDiseases(Pageable page) {
		Page<Disease> pages = diseaseRepo.findAll(page);
		List<Disease> vaccin = pages.getContent();
	      Map<String, Object> response = new HashMap<>();
	      response.put("diseases", vaccin);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalVaccin", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
}

	 @GetMapping("/disease")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Disease> getAllDisease() {
		 return diseaseRepo.findAll();
	  }
	
	@PostMapping("/disease")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?>addDisease(@RequestBody Disease disease) {
		if(diseaseRepo.existsByname(disease.getName())) {
			return new ResponseEntity<>("Error:This disease is already recorded",HttpStatus.CONFLICT);
		}
		diseaseRepo.save(disease);
		return ResponseEntity.ok(new MessageResponse("Disease recorded successfully!"));
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("disease/{id}")
	public ResponseEntity<Disease> getDiseaseById(@PathVariable("id") Long id){
		Disease disease=diseaseRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No found disease with id "+ id));	
	  return new ResponseEntity<>(disease,HttpStatus.OK);
	}
	
	@PutMapping("/disease/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Disease> updateDisease(@PathVariable("id") long id, @RequestBody Disease diseases) {
		Disease disease = diseaseRepo.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Disease with " + id + "not found"));
		disease.setDescription(diseases.getDescription());
		disease.setCause(diseases.getCause());
		disease.setName(diseases.getName());
	    return new ResponseEntity<>(diseaseRepo.save(disease), HttpStatus.OK);
	  }

	@PostMapping("/bovin/disease")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<EstMalade> isSick(@RequestBody EstMalade isSick) {
				if(!bovinRepo.existsById(isSick.getBovinid())) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				if(!diseaseRepo.existsById(isSick.getDisease())) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				estMaladeRepo.save(isSick);
	    return new ResponseEntity<>(isSick,HttpStatus.CREATED);
	  }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/bovin/disease/{id}")
	public List<EstMalade> getDiseaseBOvinyId(@PathVariable("id") Long id){
	return estMaladeRepo.findByBovinMalade(id);	
	}
	
}
