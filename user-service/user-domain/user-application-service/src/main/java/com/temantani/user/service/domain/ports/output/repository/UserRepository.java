package com.temantani.user.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.entity.User;

public interface UserRepository {

  String findEmail(String email);

  Optional<User> findById(UserId userId);

  Optional<User> findByEmail(String email);

  User save(User user);

}
