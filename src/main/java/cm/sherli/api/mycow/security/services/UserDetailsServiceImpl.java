package cm.sherli.api.mycow.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cm.sherli.api.mycow.employee.Employee;
import cm.sherli.api.mycow.employee.EmployeeRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  EmployeeRepository emplRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Employee user = emplRepo.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    return UserDetailsImpl.build(user);
  }

}
