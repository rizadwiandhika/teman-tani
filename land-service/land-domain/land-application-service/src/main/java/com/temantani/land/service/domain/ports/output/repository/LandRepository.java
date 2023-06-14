package com.temantani.land.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.entity.Land;

public interface LandRepository {
  Land save(Land land);

  Optional<Land> findById(LandId landId);

  Optional<List<Land>> findUserLands(UserId userId);

  Optional<List<Land>> findLands();
}
