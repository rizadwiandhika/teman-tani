package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ManagerJpaRepository;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.ports.output.repository.ManagerRepository;

@Repository
public class ManagerRepositoryImpl implements ManagerRepository {

  private final ManagerJpaRepository repo;
  private final ProjectDataAccessMapper mapper;
  private final EntityManager manager;

  public ManagerRepositoryImpl(ManagerJpaRepository repo, ProjectDataAccessMapper mapper, EntityManager manager) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
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
    } catch (EntityExistsException e) {
      throw new DataAlreadyExistsException("Manager already exists: " + manager.getId().getValue());
    }
  }

}
