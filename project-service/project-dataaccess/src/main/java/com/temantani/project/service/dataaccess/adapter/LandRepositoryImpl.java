package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.temantani.domain.valueobject.LandId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.LandJpaRepository;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.ports.output.repository.LandRepository;

@Repository
public class LandRepositoryImpl implements LandRepository {

  private final LandJpaRepository repo;
  private final ProjectDataAccessMapper mapper;
  private final EntityManager manager;

  public LandRepositoryImpl(LandJpaRepository repo, ProjectDataAccessMapper mapper, EntityManager manager) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
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
    } catch (EntityExistsException e) {
      throw new DataAlreadyExistsException("Land already exists: " + land.getId().getValue(), e);
    }
  }

  @Override
  public Land save(Land land) {
    return mapper.landEntityToLand(repo.save(mapper.landToLandEntity(land)));
  }

}
