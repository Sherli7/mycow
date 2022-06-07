package cm.sherli.api.mycow.reproduction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cm.sherli.api.mycow.amenagement.Troupeau;
import cm.sherli.api.mycow.bovin.BovinRepo;
import cm.sherli.api.mycow.employee.Employee;
import cm.sherli.api.mycow.employee.EmployeeRepository;
import cm.sherli.api.mycow.exception.ErrorMessageException;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mycow")
public class InseminationController {

	@Autowired InseminationRepo insemRepo;
	
	@Autowired
	BovinRepo bovinRepo;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	 @GetMapping("/inseminations/createdBy/{id}")
	 @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getCampementCreatedByPaging(@PathVariable(value = "id") Long id,Pageable page) {
				Page<Insemination> pages = insemRepo.findByCreatedBy(id,page);
				List<Insemination> camp = pages.getContent();

			      Map<String, Object> response = new HashMap<>();
			      response.put("inseminations", camp);
			      response.put("currentPage", pages.getNumber());
			      response.put("totalCreated", pages.getTotalElements());
			      response.put("totalPages", pages.getTotalPages());
			      
			      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/insemination/createdBy/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public List<Insemination> getCampementCreatedBy(@PathVariable(value = "id") Long id) {
		  List<Insemination> insem=insemRepo.findByCreatedBy(id);
		  if(insem.isEmpty()) {
			  throw new ResourceNotFoundException("Empty list");
		  }
	    return insem;
	  }
	  
	  @GetMapping("/insemination/personnel/{id}")
	  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	  public Optional<Employee> getDoneByPersonnel(@PathVariable(value = "id") Long id) {
		  Optional<Employee> insem=employeeRepository.findByPersonnel(id);
		  insem.get().getFirstname();
		  if(insem.isEmpty()) {
			  throw new ResourceNotFoundException("Empty list");
		  }
	    return insem;
	  }
	 
	  @GetMapping("/inseminations/success")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllSuccessInsem(Pageable page) {
		  Page<Insemination> pages = insemRepo.findsuccessInsem(page);
		  List<Insemination> bovin = pages.getContent();
		  Map<String, Object> response = new HashMap<>();
	      response.put("insemination", bovin);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalInsemination", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  
	  @GetMapping("/inseminations/awaiting")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllAwaitingInsem(Pageable page) {
		  Page<Insemination> pages = insemRepo.findAwaitingInsem(page);
		  List<Insemination> bovin = pages.getContent();
		  Map<String, Object> response = new HashMap<>();
	      response.put("insemination", bovin);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalInsemination", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	
	  
	@GetMapping("/inseminations")
	  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
	  public ResponseEntity<Map<String, Object>> getAllPagedInsem(Pageable page) {
		  Page<Insemination> pages = insemRepo.findAll(page);
		  List<Insemination> bovin = pages.getContent();
		  Map<String, Object> response = new HashMap<>();
	      response.put("insemination", bovin);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalInsemination", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	  @GetMapping("/insemination")
	  public List<Insemination> getAllInsemination() {
	   return insemRepo.findAll();
	  }
	
	/*
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	  @GetMapping("/insemination/success")
	  public ResponseEntity<List<Insemination>> getAllInseminationSuccess() {
	    List<Insemination> insem = new ArrayList<Insemination>();
	
	    	insemRepo.findBystatus().forEach(insem::add);
	 
	    if (insem.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(insem, HttpStatus.OK);
	  }
	*/
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("insemination/{id}")
	public ResponseEntity<Insemination> getInseminationById(@PathVariable("id") Long id){
		Insemination insemination=insemRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No found Insemination with id "+ id));	
	  return new ResponseEntity<>(insemination,HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping("/insemination/{id}")
	  public ResponseEntity<Insemination> updateInsemination(@PathVariable("id") long id, @RequestBody Insemination insemination) {
		Insemination inseminat = insemRepo.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Insemination with id " + id + " is not found"));
		inseminat.setStatus(insemination.getStatus());
	    return new ResponseEntity<>(insemRepo.save(inseminat), HttpStatus.OK);
	  }
	
	@PostMapping("/bovin/insemination/{bovinid}")
	  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
	  public ResponseEntity<Insemination> assignBovinInsemination(@PathVariable(value = "bovinid") Long bovinid, 
			  @RequestBody Insemination insemRequest) {
		String uniqueidMale=insemRequest.getUniqueidMale();
		String uniqueidFemale=insemRequest.getUniqueidFemale();
		String dateInsem=insemRequest.getDateInsem();
		
		  DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			        .appendPattern("dd-MM-uuuu")
			        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			        .toFormatter();
	    	LocalDate dateInsemimation=LocalDate.parse(dateInsem,formatter);
	    	LocalDate tomorrow = LocalDate.now().plusDays(1);
	    	  if (tomorrow.isBefore(dateInsemimation)) {
		 	    	throw new ErrorMessageException ("La date de l'insémination ne doit pas être supérieure à la date d'aujourd'hui.");
	    	  }
	    	  insemRequest.setDateInsem(dateInsem);
	    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
	    		Calendar c = Calendar.getInstance();
	    		try{
	    		   c.setTime(sdf.parse(dateInsem));
	    		}catch(ParseException e){
	    		   e.printStackTrace();
	    		 }
	    		//Incrementing the date by 1 day
	    		c.add(Calendar.DAY_OF_MONTH, 30); 
	    		String newDate = sdf.format(c.getTime());  
	    		LocalDate dateFec=LocalDate.parse(newDate,formatter);
	    		System.out.println("Date Incremented by One: "+newDate);
	    		
	    		insemRequest.setDateFec(dateFec.toString());
		     Insemination tag = (Insemination) bovinRepo.findById(bovinid).map(insem -> {
	        
			if(!bovinRepo.existsByuniqueid(uniqueidMale) && !bovinRepo.existsByuniqueid(uniqueidFemale)) {
				throw new ResourceNotFoundException(uniqueidMale +" and "+uniqueidFemale +" codes not found");
			}
			if(!bovinRepo.existsByuniqueid(uniqueidFemale)) {
				throw new ResourceNotFoundException(uniqueidFemale +" code not found");
			}
			if(!bovinRepo.existsByuniqueid(uniqueidMale)) {
				throw new ResourceNotFoundException(uniqueidMale +" code not found");
			}
			
			if(uniqueidFemale==uniqueidMale) {
				throw new ErrorMessageException("Please choose two specimens of different sex");
			}
			
			
			insemRequest.setCodeSemence(uniqueidFemale+"-"+uniqueidFemale);
			//StatusCode=0, en attente, -1 échec, 1 réussi
	        insemRequest.setStatus("awaiting");
	        	      // add and create new Tag
	        insem.addInsemination(insemRequest);
	        return insemRepo.save(insemRequest);
	      }).orElseThrow(() -> new ResourceNotFoundException("Not found bovin with id = " + bovinid));

	    return new ResponseEntity<>(tag,HttpStatus.CREATED);
	  }

	@DeleteMapping("/insem/del")
	public void deleteAllInsem() {
	 insemRepo.deleteAll();
	}
	
	
}












