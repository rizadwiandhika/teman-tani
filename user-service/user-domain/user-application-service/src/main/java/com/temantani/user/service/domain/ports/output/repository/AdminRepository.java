package com.temantani.user.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.entity.Admin;

public interface AdminRepository {
  String findEmail(String email);

  Optional<Admin> findById(UserId userId);

  Optional<Admin> findByEmail(String email);

  Admin save(Admin user);
}
