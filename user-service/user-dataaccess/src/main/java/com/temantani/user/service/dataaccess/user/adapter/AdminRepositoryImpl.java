package com.temantani.user.service.dataaccess.user.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.dataaccess.user.mapper.UserDataAccessMapper;
import com.temantani.user.service.dataaccess.user.repository.AdminJpaRepository;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.ports.output.repository.AdminRepository;

@Component
public class AdminRepositoryImpl implements AdminRepository {

  private final AdminJpaRepository adminRepo;
  private final UserDataAccessMapper mapper;

  public AdminRepositoryImpl(AdminJpaRepository adminRepo, UserDataAccessMapper mapper) {
    this.adminRepo = adminRepo;
    this.mapper = mapper;
  }

  @Override
  public Optional<Admin> findByEmail(String email) {
    return adminRepo.findByEmail(email).map(mapper::adminEntityToAdmin);
  }

  @Override
  public Optional<Admin> findById(UserId userId) {
    return adminRepo.findById(userId.getValue()).map(mapper::adminEntityToAdmin);
  }

  @Override
  public String findEmail(String email) {
    return findByEmail(email).map(Admin::getEmail).orElse("");
  }

  @Override
  public Admin save(Admin admin) {
    return mapper.adminEntityToAdmin(adminRepo.save(mapper.adminToAdminEntity(admin)));
  }

}
