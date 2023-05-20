package com.temantani.user.service.domain.service;

public interface UserPasswordEncoder {

  String encode(CharSequence rawPassword);

  Boolean matches(String rawPassword, String encodedPassword);

}
