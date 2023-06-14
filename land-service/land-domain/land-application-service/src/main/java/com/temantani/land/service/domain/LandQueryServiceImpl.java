package com.temantani.land.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.query.LandData;
import com.temantani.land.service.domain.dto.query.LandDetailsData;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.mapper.LandDataMapper;
import com.temantani.land.service.domain.ports.input.service.LandQueryService;
import com.temantani.land.service.domain.ports.output.repository.LandRepository;

@Service
public class LandQueryServiceImpl implements LandQueryService {

  private final LandRepository landRepository;
  private final LandDataMapper mapper;

  public LandQueryServiceImpl(LandRepository landRepository, LandDataMapper mapper) {
    this.landRepository = landRepository;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<LandData> getAllLands() {
    return landRepository.findLands()
        .map(lands -> lands.stream()
            .map(mapper::landToLandData)
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  @Transactional(readOnly = true)
  public LandDetailsData getLandDetails(LandId landId) {
    return landRepository.findById(landId)
        .map(mapper::landToLandDetailsData)
        .orElseThrow(() -> new DataNotFoundException("Land: " + landId.getValue() + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public LandDetailsData getUserLandDetails(UserId userId, LandId landId) {
    Land land = landRepository.findById(landId)
        .orElseThrow(() -> new DataNotFoundException("Land: " + landId.getValue() + " not found"));

    if (land.getOwnerId().equals(userId) == false) {
      new DataNotFoundException("Land: " + landId.getValue() + " not found");
    }

    return mapper.landToLandDetailsData(land);
  }

  @Override
  @Transactional(readOnly = true)
  public List<LandData> getUserOwnedLands(UserId userId) {
    return landRepository.findUserLands(userId).map((lands) -> lands.stream()
        .map(mapper::landToLandData)
        .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

}
