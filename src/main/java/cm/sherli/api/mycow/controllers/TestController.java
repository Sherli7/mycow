package cm.sherli.api.mycow.controllers;

import java.io.Console;
import java.util.List;

import cm.sherli.api.mycow.localisation.Department;
import cm.sherli.api.mycow.localisation.DepartmentRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  private final DepartmentRepo departmentRepo;

  public TestController(DepartmentRepo departmentRepo) {
    this.departmentRepo = departmentRepo;
  }

  @GetMapping("/all/{id}")
  public List<Department> allAccess(@PathVariable("id") int id) {
    return departmentRepo.findByRegionId(id);
  }

  @GetMapping("/user")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
	Console.class.getProtectionDomain();
    return "Admin Board.";
  }
}
