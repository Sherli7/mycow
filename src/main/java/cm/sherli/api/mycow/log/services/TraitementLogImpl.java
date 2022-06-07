package cm.sherli.api.mycow.log.services;

import javax.transaction.Transactional;

import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cm.sherli.api.mycow.log.model.TraitementLog;
import cm.sherli.api.mycow.log.repo.TraitementLogRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TraitementLogImpl implements TraitementService{

    private final TraitementLogRepo traitementLogRepo;

    @Override
    public TraitementLog traitIssue(TraitementLog issue) {

        return traitementLogRepo.save(issue);
    }
    public ResponseEntity<Map<String, Object>> getTraitementBovinLogPaged(Long id,Pageable page) {
        Page<TraitementLog> pages = traitementLogRepo.findBybovinid(id,page);
        List<TraitementLog> log = pages.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("traitementlog", log);
        response.put("currentPage", pages.getNumber());
        response.put("totalTraitement", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public ResponseEntity<TraitementLog> updateTraitementIssue(long id, TraitementLog traitementLog) {
        TraitementLog traitid=traitementLogRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Traitement "+id+" not found"));
        traitid.setIssue(traitementLog.getIssue());
        return new ResponseEntity<>(traitementLogRepo.save(traitid),HttpStatus.OK);
    }
}
