package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.LandId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.LandJpaRepository;
import com.temantani.project.service.dataaccess.util.ProjectDataAccessUtil;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.ports.output.repository.LandRepository;

@Component
public class LandRepositoryImpl implements LandRepository {

  private final LandJpaRepository repo;
  private final ProjectDataAccessMapper mapper;
  private final EntityManager manager;
  private final ProjectDataAccessUtil util;

  public LandRepositoryImpl(LandJpaRepository repo, ProjectDataAccessMapper mapper, EntityManager manager,
      ProjectDataAccessUtil util) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
    this.util = util;
  }

  @Override
  public Optional<Land> findById(LandId landId) {
    return repo.findById(landId.getValue()).map(mapper::landEntityToLand);
  }

  @Override
  public Land create(Land land) throws DataAlreadyExistsException {
    try {
      manager.persist(mapper.landToLandEntity(land));
      manager.flush();

      return repo.findById(land.getId().getValue()).map(mapper::landEntityToLand).orElse(null);
    } catch (PersistenceException e) {
      if (util.isUniqueViolation(e)) {
        throw new DataAlreadyExistsException("Land already exists: " + land.getId().getValue(), e);
      }
      throw e;
    }
  }

  @Override
  public Land save(Land land) {
    return mapper.landEntityToLand(repo.save(mapper.landToLandEntity(land)));
  }

}
