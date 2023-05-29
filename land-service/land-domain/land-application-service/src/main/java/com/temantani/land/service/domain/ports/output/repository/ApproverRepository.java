package com.temantani.land.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.exception.DataAlreadyExists;

public interface ApproverRepository {

  Optional<Approver> findById(UserId approverId);

  Approver save(Approver approver) throws DataAlreadyExists;

}
