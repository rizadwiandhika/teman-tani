package com.temantani.land.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.entity.Approver;

public interface ApproverRepository {

  Optional<Approver> findById(UserId approverId);

}
