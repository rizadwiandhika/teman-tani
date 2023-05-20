package com.temantani.land.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.LandId;
import com.temantani.land.service.domain.entity.Land;

public interface LandRepository {
  Land save(Land land);

  Optional<Land> findById(LandId landId);
}
