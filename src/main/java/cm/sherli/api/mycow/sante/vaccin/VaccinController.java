package cm.sherli.api.mycow.sante.vaccin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mycow")
public class VaccinController {

@Autowired VaccinRepo vaccinRepo;
@Autowired VaccinServiceImpl vaccinService;
@Autowired BovinRepo bovinRepo;

	
	  @GetMapping("/vaccines")
	  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Map<String, Object>> getAllVaccinsPaged(Pageable page) {
		 return vaccinService.getAllVaccinsPaged(page);
	  }
		@GetMapping("/vaccine")
		@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
		  public List<Vaccin> getAllVaccinWithoutPagination() {
			 return vaccinService.getAllVaccinWithoutPagination();
		  }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/vaccine")
	public ResponseEntity<?>addVaccin(@RequestBody Vaccin vaccin) {
		vaccinService.addVaccin(vaccin);
		return new ResponseEntity<>(vaccin,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("vaccine/{id}")
	public ResponseEntity<Vaccin> getVaccineById(@PathVariable("id") Long id){
		Vaccin vaccin=vaccinRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found vaccine with id "+ id));	
	  return new ResponseEntity<>(vaccin,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	@PutMapping("/vaccine/{id}")
	  public ResponseEntity<Vaccin> updateVaccine(@PathVariable("id") long id, @RequestBody Vaccin vaccines) {
	    return vaccinService.updateVaccine(id, vaccines);
	  }
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	@PutMapping("/vaccine/prohibited/{id}")
	  public ResponseEntity<Vaccin> updateVaccineProhibited(@PathVariable("id") long id) {
		return vaccinService.updateVaccineProhibited(id);
	  }
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	@PutMapping("/vaccine/approved/{id}")
	  public ResponseEntity<Vaccin> updateVaccineApproved(@PathVariable("id") long id) {
		return vaccinService.updateVaccineApproved(id);
	  }
	
	@PostMapping("/bovin/vaccine/{bovinid}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Vaccin> assignBovinVaccin(@PathVariable(value = "bovinid") Long bovinid, 
			  @RequestBody Vaccin troupeauRequest	  ) {
return vaccinService.assignBovinVaccin(bovinid, troupeauRequest);
	  }


	
	
}
