package com.temantani.user.service.dataaccess.user.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.dataaccess.user.entity.UserEntity;
import com.temantani.user.service.dataaccess.user.mapper.UserDataAccessMapper;
import com.temantani.user.service.dataaccess.user.repository.UserJpaRepository;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.ports.output.repository.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;
  private final UserDataAccessMapper mapper;

  public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserDataAccessMapper mapper) {
    this.userJpaRepository = userJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<User> findById(UserId userId) {
    return userJpaRepository.findById(userId.getValue()).map(mapper::userEntityToUser);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userJpaRepository.findByEmail(email).map(mapper::userEntityToUser);
  }

  @Override
  public String findEmail(String email) {
    return findByEmail(email).map(User::getEmail).orElse("");
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = userJpaRepository.save(mapper.userToUserEntity(user));
    return mapper.userEntityToUser(userEntity);
  }

}
