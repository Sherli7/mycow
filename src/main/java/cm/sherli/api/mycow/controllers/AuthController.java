package cm.sherli.api.mycow.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.sherli.api.mycow.amenagement.CampementRepository;
import cm.sherli.api.mycow.amenagement.RanchRepository;
import cm.sherli.api.mycow.email.EmailBuilder;
import cm.sherli.api.mycow.email.EmailSender;
import cm.sherli.api.mycow.email.EmailValidator;
import cm.sherli.api.mycow.employee.Employee;
import cm.sherli.api.mycow.employee.EmployeeRepository;
import cm.sherli.api.mycow.exception.ResourceNotAuthorized;
import cm.sherli.api.mycow.exception.ResourceNotFoundException;
import cm.sherli.api.mycow.group.RoleRepository;
import cm.sherli.api.mycow.payload.request.LoginRequest;
import cm.sherli.api.mycow.payload.request.SignupRequest;
import cm.sherli.api.mycow.payload.response.JwtResponse;
import cm.sherli.api.mycow.payload.response.MessageResponse;
import cm.sherli.api.mycow.security.PasswordGenerator;
import cm.sherli.api.mycow.security.jwt.JwtUtils;
import cm.sherli.api.mycow.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mycow/auth")
public class AuthController {
  private final
  AuthenticationManager authenticationManager;

  private final EmployeeRepository userRepository;
  private final RanchRepository ranchRepository;

  private final RoleRepository roleRepository;

  private final CampementRepository campementRepository;
  private final EmailValidator emailValidator;
  private final EmailSender emailSender;
  private final  JwtUtils jwtUtils;
  private final  PasswordEncoder encoder;
  
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	  
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    boolean activeAccount=userDetails.isActiveAccount();
    boolean lockedAccount=userDetails.isLockedAccount();
    if(activeAccount && lockedAccount) {
    	throw new  ResourceNotAuthorized("Your account has been locked");
    }
    if(!lockedAccount && !activeAccount) {
    	throw new  ResourceNotAuthorized("Please contact administrator.");
    }
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getFirstname(),  
                         userDetails.getLastname(),
                         userDetails.getCni(),
                         userDetails.isActiveAccount(),
                         userDetails.isRequestor(),
                         userDetails.isLockedAccount(),
                         userDetails.getEmail(),
                         userDetails.getCampid(),
                         userDetails.getRanchid(),
                         roles));
  }

  @PostMapping("/act")
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeRequest) {

		  ranchRepository.findById(employeeRequest.getRanchid())
		    .orElseThrow(() -> new ResourceNotFoundException("Not found Ranch with id : "+employeeRequest.getRanchid()));
		  campementRepository.findById(employeeRequest.getCampid())
		    .orElseThrow(() -> new ResourceNotFoundException("Not found campement with id : "+employeeRequest.getRanchid()));
	  
	 String beforehashpassgen=PasswordGenerator.generateStrongPassword();
   	 String password=encoder.encode(beforehashpassgen);
   	 employeeRequest.setPassword(password);
     emailSender.send(
   			 employeeRequest.getEmail(),
   	             EmailBuilder.buildEmail(
   	            		 employeeRequest.getFirstname()+" "+
   	            		 employeeRequest.getLastname() , beforehashpassgen));
     userRepository.save(employeeRequest);
    return new ResponseEntity<>(employeeRequest, HttpStatus.CREATED);
  }
  
  @PostMapping("/signup")
  public ResponseEntity<?> registerUserWithoutRole(@RequestBody SignupRequest signUpRequest) {

	  if(signUpRequest.getEmail()==null) {
		  return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Please enter your email"));
	  }
	 
	  if(!userRepository.existsByEmail(signUpRequest.getEmail())) {
  		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  	}
    // Create new user's account

	 String beforehashpassgen=PasswordGenerator.generateStrongPassword();
	 String password=encoder.encode(beforehashpassgen);
	  Employee employee = userRepository.findByEmail(signUpRequest.getEmail())
		        .orElseThrow(() -> new ResourceNotFoundException("User with email " + signUpRequest.getEmail() + " not found"));

	  try {
		  emailSender.send(
		    		signUpRequest.getEmail(),
		    		
		            EmailBuilder.buildEmails("MYCOW USER", beforehashpassgen));
		  System.out.println(beforehashpassgen);
	} catch (Exception e) {
		throw new IllegalStateException("L'envoi d'email n'a pas réussi");
	}
    employee.setPassword(password);
	 employee.setRequestor(true);
	 userRepository.save(employee);
    return ResponseEntity.ok(new MessageResponse("Account created!Please check your email account if it exists. "));
  }
  @PostMapping("/initializr")
  public ResponseEntity<Employee> initializr(@RequestBody Employee empl
  ) {
	  empl.setId(empl.getId());
    return new ResponseEntity<>(userRepository.save(empl), HttpStatus.CREATED);
  }
  
}
