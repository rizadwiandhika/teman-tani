package com.temantani.project.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.LandId;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;

public interface LandRepository {

  Optional<Land> findById(LandId landId);

  Land save(Land land);

  Land create(Land land) throws DataAlreadyExistsException;

}
