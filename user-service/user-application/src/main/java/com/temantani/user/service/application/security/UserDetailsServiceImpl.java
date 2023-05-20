package com.temantani.user.service.application.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.ports.output.repository.AdminRepository;
import com.temantani.user.service.domain.ports.output.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;

  public UserDetailsServiceImpl(UserRepository userRepository, AdminRepository adminRepository) {
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("[UserDetailsServiceImpl] loading email: {}", email);

    return userRepository
        .findByEmail(email)
        .map(this::userToUserDetails)
        .or(() -> adminRepository.findByEmail(email).map(this::adminToUserDetails))
        .orElseThrow(() -> new UsernameNotFoundException("User email: " + email + " not found"));
  }

  private UserDetails adminToUserDetails(Admin admin) {
    return UserDetailsImpl
        .builder()
        .username(admin.getId().getValue().toString())
        .password(admin.getPassword())
        .roles(new ArrayList<>(Arrays.asList(admin.getRole().name())))
        .build();
  }

  private UserDetails userToUserDetails(User user) {
    log.info("password: {}", user.getPassword());

    return UserDetailsImpl
        .builder()
        .username(user.getId().getValue().toString())
        .password(user.getPassword())
        .roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
        .build();
  }

}
