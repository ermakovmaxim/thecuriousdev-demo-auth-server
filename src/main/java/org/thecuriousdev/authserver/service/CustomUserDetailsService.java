package org.thecuriousdev.authserver.service;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thecuriousdev.authserver.entity.User;
import org.thecuriousdev.authserver.repository.UserRepository;
import org.thecuriousdev.authserver.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void test() {
    User user = new User();
    user.setUsername("planevik");
    user.setPassword("password");
    user.setRoles(Arrays.asList("ADMIN", "USER"));

    if (!userRepository.findById("planevik").isPresent()) {
      userRepository.create(user);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    return new CustomUserDetails(userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
  }
}
