package com.temantani.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.temantani.user.service.domain.service.UserPasswordEncoder;

@Component
public class UserPasswordEncoderImpl implements UserPasswordEncoder {

  private PasswordEncoder passwordEncoder;

  public UserPasswordEncoderImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(CharSequence rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public Boolean matches(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

}
