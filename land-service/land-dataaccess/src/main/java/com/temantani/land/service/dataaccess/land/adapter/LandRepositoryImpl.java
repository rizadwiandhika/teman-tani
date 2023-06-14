package com.temantani.land.service.dataaccess.land.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.land.entity.LandEntity;
import com.temantani.land.service.dataaccess.land.mapper.LandDataAccessMapper;
import com.temantani.land.service.dataaccess.land.repository.LandJpaRepository;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.ports.output.repository.LandRepository;

@Component
public class LandRepositoryImpl implements LandRepository {

  private final LandJpaRepository landJpaRepository;
  private final LandDataAccessMapper mapper;

  public LandRepositoryImpl(LandJpaRepository landJpaRepository, LandDataAccessMapper mapper) {
    this.landJpaRepository = landJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Land save(Land land) {
    return mapper.landEntityToLand(landJpaRepository.save(mapper.landToLandEntity(land)));
  }

  @Override
  public Optional<Land> findById(LandId landId) {
    return landJpaRepository.findById(landId.getValue()).map(mapper::landEntityToLand);
  }

  @Override
  public Optional<List<Land>> findLands() {
    List<LandEntity> lands = landJpaRepository.findAll();
    if (lands == null) {
      return Optional.empty();
    }

    return Optional.of(lands.stream().map(mapper::landEntityToLand).collect(Collectors.toList()));
  }

  @Override
  public Optional<List<Land>> findUserLands(UserId userId) {
    return landJpaRepository.findByOwnerId(userId.getValue())
        .map(lands -> lands.stream().map(mapper::landEntityToLand).collect(Collectors.toList()));
  }

}
