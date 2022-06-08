package cm.sherli.api.mycow.log.controller;

import cm.sherli.api.mycow.log.model.TraitementLog;
import cm.sherli.api.mycow.log.services.TraitementLogImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mycow")
@RequiredArgsConstructor
public class TraitementLogController {

    private final TraitementLogImpl traitementService;


    @PutMapping("/bovin/traitement/log/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<TraitementLog> updateTraitementIssue(@PathVariable("id") long id,@RequestBody TraitementLog log){
        return traitementService.updateTraitementIssue(id,log);
    }


    @GetMapping("/bovin/traitement/log/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Map<String, Object>> getAllTraitementByBovinid(@PathVariable("id") long id, Pageable page){
        return traitementService.getTraitementBovinLogPaged(id,page);
    }

}
