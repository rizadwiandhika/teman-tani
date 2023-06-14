package com.temantani.land.service.domain.ports.input.service;

import java.util.List;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.query.LandData;
import com.temantani.land.service.domain.dto.query.LandDetailsData;

public interface LandQueryService {

  List<LandData> getUserOwnedLands(UserId userId);

  LandDetailsData getUserLandDetails(UserId userId, LandId landId);

  List<LandData> getAllLands();

  LandDetailsData getLandDetails(LandId landId);

}
