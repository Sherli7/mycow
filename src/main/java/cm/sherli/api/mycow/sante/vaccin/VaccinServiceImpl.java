package cm.sherli.api.mycow.sante.vaccin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cm.sherli.api.mycow.bovin.BovinRepo;
import cm.sherli.api.mycow.employee.Employee;
import cm.sherli.api.mycow.employee.EmployeeRepository;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.log.model.BovinLog;
import cm.sherli.api.mycow.log.model.VaccinLog;
import cm.sherli.api.mycow.log.repo.BovinLogRepo;
import cm.sherli.api.mycow.log.services.BovinLogService;
import cm.sherli.api.mycow.log.repo.VaccinLogRepo;
import cm.sherli.api.mycow.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VaccinServiceImpl implements VaccinLogService,BovinLogService {

	private final VaccinRepo vaccinRepo;

	private final BovinRepo bovinRepo;
	
	private final EmployeeRepository employeeRepository;
	
	private final BovinLogRepo bovinLogRepo;
	
	private final VaccinLogRepo vaccinLogRepo;
	
	public List<Vaccin> getAllVaccinWithoutPagination() {
		 return vaccinRepo.findAll();
	  }
	
	public ResponseEntity<Map<String, Object>> getAllVaccinsPaged(Pageable page) {
		Page<Vaccin> pages = vaccinRepo.findAll(page);
		List<Vaccin> vac = pages.getContent();
	      Map<String, Object> response = new HashMap<>();
	      response.put("vaccins", vac);
	      response.put("currentPage", pages.getNumber());
	      response.put("totalVaccin", pages.getTotalElements());
	      response.put("totalPages", pages.getTotalPages());
	      
	      return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<?>addVaccin(Vaccin vaccin) {
		vaccinRepo.save(vaccin);
		saveVaccinLog(new VaccinLog(null,vaccin.getName(),vaccin.getLabo(),"Adding vaccine",vaccin.getCreatedBy().toString(),vaccin.getCreatedAt().toLocalDate().toString()));
		return ResponseEntity.ok(new MessageResponse("Vaccine recorded successfully!"));
	}

	
	 public ResponseEntity<Vaccin> updateVaccine(long id, Vaccin vaccines) {
			Vaccin vaccin = vaccinRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Vaccine with " + id + "not found"));
			vaccin.setDescription(vaccines.getDescription());
			vaccin.setName(vaccines.getLabo());
			vaccin.setName(vaccines.getName());
		    return new ResponseEntity<>(vaccinRepo.save(vaccin), HttpStatus.OK);
		  }
		
	 
	 public ResponseEntity<Vaccin> updateVaccineApproved(long id) {
			Vaccin vaccin = vaccinRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Vaccine with " + id + "not found"));
			vaccin.setProhibited(false);
				log.info("Vaccin logged as false", HttpStatus.OK);
				Employee logger=employeeRepository.findById(vaccin.getCreatedBy()).orElseThrow(()-> new ResourceNotFoundException("Person not found"));
				saveVaccinLog(new VaccinLog(null,vaccin.getName(),vaccin.getLabo(),"Vaccine approuved ",logger.getFirstname()+" "+logger.getLastname(),vaccin.getCreatedAt().toString()));
			return new ResponseEntity<>(vaccinRepo.save(vaccin), HttpStatus.OK);
		  }
		
	 public ResponseEntity<Vaccin> updateVaccineProhibited(long id) {
			Vaccin vaccin = vaccinRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Vaccine with " + id + "not found"));
			vaccin.setProhibited(true);
				log.info("Vaccin logged as false", HttpStatus.OK);
				Employee logger=employeeRepository.findById(vaccin.getCreatedBy()).orElseThrow(()-> new ResourceNotFoundException("Person not found"));
				saveVaccinLog(new VaccinLog(null,vaccin.getName(),vaccin.getLabo(),"Vaccine prohibited ",logger.getFirstname()+" "+logger.getLastname(),vaccin.getCreatedAt().toString()));
			return new ResponseEntity<>(vaccinRepo.save(vaccin), HttpStatus.OK);
		  }
	  public ResponseEntity<Vaccin> assignBovinVaccin(Long bovinid, 
			Vaccin vaccinRequest	  ) {
		Vaccin tag = bovinRepo.findById(bovinid).map(troup -> {
	        long tagId = vaccinRequest.getId();
	        // tag is existed
	        if (tagId != 0L) {
	        	Vaccin _tag = vaccinRepo.findById(tagId)
	              .orElseThrow(() -> new ResourceNotFoundException("Not found Vaccin with id = " + tagId));
	        	troup.addVaccination(_tag);
	          bovinRepo.save(troup);
	          return _tag;
	        }
	       
	        // add and create new Tag
	        troup.addVaccination(vaccinRequest);
	        return vaccinRepo.save(vaccinRequest);
	      }).orElseThrow(() -> new ResourceNotFoundException("Not found bovin with id = " + bovinid));
		 Employee logger=employeeRepository.findById(tag.getCreatedBy()).orElseThrow(()-> new ResourceNotFoundException("Person not found"));
		saveBovinLog(new BovinLog(null,bovinid,"Received the "+tag.getName()+" vaccine ",logger.getFirstname()+" "+logger.getFirstname(),tag.getCreatedAt().toString()));
	    return new ResponseEntity<>(tag,HttpStatus.CREATED);
	  }

	@Override
	public VaccinLog saveVaccinLog(VaccinLog vaccinLog) {
		return vaccinLogRepo.save(vaccinLog);
	}

	@Override
	public VaccinLog getVaccinLogByAction(String action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VaccinLog> getAllVaccinLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<VaccinLog> getVaccinLogByVaccinId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BovinLog saveBovinLog(BovinLog bovinLog) {
		bovinLogRepo.save(bovinLog);
		return null;
	}

	@Override
	public BovinLog getBovinLogByAction(String action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BovinLog> getAllBovinLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BovinLog> getBovinLogByBovinId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
