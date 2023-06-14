package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ManagerJpaRepository;
import com.temantani.project.service.dataaccess.util.ProjectDataAccessUtil;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.ports.output.repository.ManagerRepository;

@Component
public class ManagerRepositoryImpl implements ManagerRepository {

  private final ManagerJpaRepository repo;
  private final ProjectDataAccessMapper mapper;
  private final EntityManager manager;
  private final ProjectDataAccessUtil util;

  public ManagerRepositoryImpl(ManagerJpaRepository repo, ProjectDataAccessMapper mapper, EntityManager manager,
      ProjectDataAccessUtil util) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
    this.util = util;
  }

  @Override
  public Optional<Manager> findById(UserId managerId) {
    return repo.findById(managerId.getValue()).map(mapper::managerEntityToManager);
  }

  @Override
  public Manager create(Manager manager) {
    try {
      this.manager.persist(mapper.managerToManagerEntity(manager));
      this.manager.flush();

      return repo.findById(manager.getId().getValue()).map(mapper::managerEntityToManager).orElse(null);
    } catch (PersistenceException e) {
      if (util.isUniqueViolation(e)) {
        throw new DataAlreadyExistsException("Manager already exists: " + manager.getId().getValue(), e);
      }
      throw e;
    }
  }

}
