package cm.sherli.api.mycow.bovin;

import cm.sherli.api.mycow.amenagement.CampementRepository;
import cm.sherli.api.mycow.amenagement.RanchRepository;
import cm.sherli.api.mycow.amenagement.TroupeauRepo;
import cm.sherli.api.mycow.exception.ErrorMessageException;
import cm.sherli.api.mycow.exception.ResourceAlreadyExists;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.log.model.BovinLog;
import cm.sherli.api.mycow.log.repo.BovinLogRepo;
import cm.sherli.api.mycow.log.services.BovinLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BovinService implements BovinLogService  {

	
	private final BovinRepo bovinRepo;
	
	private final BovinLogRepo bovinLogRepo;

	private final TroupeauRepo troupRepo;

	private final RanchRepository ranchRepository;

	private final CampementRepository campRepository;
	
	public Bovin findBybovinid(Long bovinid) {
		return bovinRepo.findBybovinid(bovinid);
	}

	 public ResponseEntity<Bovin> updateBovinStatus(long id,Bovin bovin,BovinLog log) {
		    Bovin bov = bovinRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("BovinId " + id + "not found"));
			bov.setStatus(bovin.getStatus());
			bovinLogRepo.save(new BovinLog(null,id,"Update bovin status to "+bovin.getStatus(),log.getMadeBy(),log.getMadeAt()));
		    return new ResponseEntity<>(bovinRepo.save(bov), HttpStatus.OK);
	}
	 
	
	 public ResponseEntity<Map<String, Object>> getAllBovins(Pageable page) {
			Page<Bovin> pages = bovinRepo.findAll(page);
			if(!pages.hasContent()) {
				throw new ResourceNotFoundException("This page is empty");
			}
			List<Bovin> bovins = pages.getContent();
		    Map<String, Object> response = new HashMap<>();
		    response.put("bovins", bovins);
		    response.put("currentPage", pages.getNumber());
		    response.put("totalBovin", pages.getTotalElements());
		    response.put("totalPages", pages.getTotalPages());		      
		      return new ResponseEntity<>(response, HttpStatus.OK);
	  }

	 public Bovin getBovinById(Long id){
		return bovinRepo.findById(id)			
				.orElseThrow(()-> new ResourceNotFoundException("Bovin "+ id+" not found"));	
		}

	 public List<Bovin> getBovinByRanch(Long ranchid) {  
		 return Optional.ofNullable(ranchRepository.findById(ranchid))
		 .map(bov-> bovinRepo.findByBovin(ranchid))
		 .orElse(null);
	  }
	  
	 public List<Bovin> getBovinByTroupeau(Long troupid) {        
		 if(!troupRepo.existsById(troupid)) {
			 throw new ResourceNotFoundException("Troupeau "+troupid+" not found");
		 }
		  List<Bovin> bov = bovinRepo.findByBovintroupeau(troupid);
		return bov;
	  }

	  public ResponseEntity<Bovin> updateBovin(long id, Bovin bovin) {
		  Bovin bov = bovinRepo.findById(id)
			        .orElseThrow(() -> new ResourceNotFoundException("BovinId " + id + "not found"));
			    String BIRTHDAY=bovin.getBirthDay();
				  DateTimeFormatter formatter = new DateTimeFormatterBuilder()
					        .appendPattern("dd-MM-uuuu")
					        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
					        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
					        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
					        .toFormatter();
			    	LocalDate birthday=LocalDate.parse(BIRTHDAY,formatter);
			    	LocalDate tomorrow = LocalDate.now().plusDays(1);
			    	  if (tomorrow.isBefore(birthday)) {
				 	    	throw new ErrorMessageException ("Please check the date. Date of birth must be no later than this day.");
			    	 }
			    	  
			    bov.setBirthDay(BIRTHDAY);		    
			    bov.setRace(bovin.getRace());
			    bov.setCornage(bovin.getCornage());
			    bov.setRobe(bovin.getRobe());
				bov.setFirstPhysicId(bovin.getFirstPhysicId());
				bov.setSecPhysicId(bovin.getSecPhysicId());
				bov.setSex(bovin.getSex());
				bov.setModeReproduction(bovin.getModeReproduction());
				bov.setHeightAtBirth(bovin.getHeightAtBirth());
				bov.setWeightAtBirth(bovin.getWeightAtBirth());
			    bov.setUpdatedAt(LocalDateTime.now());
			    savebovinlog(new BovinLog(null,bov.getBovinid(),"Update bovin","superadmin","01-01-2022"));
			    return new ResponseEntity<>(bovinRepo.save(bov), HttpStatus.OK);
	  }

	  public ResponseEntity<Bovin> createBovins(Long ranchid,Long troupid,Long campid,Bovin bovinRequest) {
		  String BIRTHDAY=bovinRequest.getBirthDay();
		  DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			        .appendPattern("dd-MM-uuuu")
			        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			        .toFormatter();
	    	LocalDate birthday=LocalDate.parse(BIRTHDAY,formatter);
	    	LocalDate tomorrow = LocalDate.now().plusDays(1);
	    	 String ranch=ranchRepository.findByName(ranchid);
	    	 String campe=campRepository.findByName(campid);
	    	 
	         campe.replaceAll("\\s+", "");
             BIRTHDAY.replaceAll("-", "");
	         ranch.replaceAll("\\s+", "");

	    	 String uniqueid=
			         ranch.replaceAll("\\s+", "")+"-"+
			         campe.replaceAll("\\s+", "")+"-"+
			         BIRTHDAY.replaceAll("-", "")+"-"+
			         bovinRequest.getFirstPhysicId().replaceAll("\\s+", "")+"-"+
					 bovinRequest.getCornage().replaceAll("\\s+", "")+"-"+
					 bovinRequest.getRobe().replaceAll("\\s+", "")+"-"+
					 bovinRequest.getRace().replaceAll("\\s+", "");
	    	   
	        bovinRequest.setUniqueid(uniqueid);
	    	if(bovinRepo.existsByuniqueid(bovinRequest.getUniqueid())) {
	    		throw new ResourceAlreadyExists("Already saved!",HttpStatus.CONFLICT);
	    	}
	    	  if (tomorrow.isBefore(birthday)) {
		 	    	throw new ErrorMessageException ("Please check the date. Date of birth must not be later than this day.");
	    	  }
	    	  
	    Bovin troupeau = troupRepo.findById(troupid).map(camp -> {
	    	bovinRequest.setTroupeau(camp);
	      return bovinRepo.save(bovinRequest);
	    }).orElseThrow(() -> new ResourceNotFoundException("Not found troupeau with id = " + troupid));

	    return new ResponseEntity<>(troupeau, HttpStatus.CREATED);
	  }
	  
	  public ResponseEntity<List<Bovin>> findDeletedBovins() {
		    List<Bovin> bovines = bovinRepo.findByIsDelete(true);
		    if (bovines.isEmpty()) {
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }	    
		    return new ResponseEntity<>(bovines, HttpStatus.OK);
		  }


		@Override
		public BovinLog savebovinlog(BovinLog bovinLog) {
			return bovinLogRepo.save(bovinLog);
		}
	

	
}
