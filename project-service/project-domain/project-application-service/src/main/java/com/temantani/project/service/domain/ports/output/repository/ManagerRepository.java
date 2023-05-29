package com.temantani.project.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.entity.Manager;

public interface ManagerRepository {

  Optional<Manager> findById(UserId managerId);

  Manager create(Manager manager);

}
