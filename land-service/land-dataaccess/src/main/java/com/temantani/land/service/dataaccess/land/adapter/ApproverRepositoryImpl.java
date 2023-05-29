package com.temantani.land.service.dataaccess.land.adapter;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.land.entity.ApproverEntity;
import com.temantani.land.service.dataaccess.land.mapper.ApproverDataAccessMapper;
import com.temantani.land.service.dataaccess.land.repository.ApproverJpaRepository;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.exception.DataAlreadyExists;
import com.temantani.land.service.domain.ports.output.repository.ApproverRepository;

@Component
public class ApproverRepositoryImpl implements ApproverRepository {

  private final ApproverJpaRepository approverJpaRepository;
  private final ApproverDataAccessMapper mapper;

  public ApproverRepositoryImpl(ApproverJpaRepository approverJpaRepository, ApproverDataAccessMapper mapper) {
    this.approverJpaRepository = approverJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Approver> findById(UserId approverId) {
    return approverJpaRepository.findById(approverId.getValue()).map(mapper::approverEntityToApprover);
  }

  @Override
  public Approver save(Approver approver) throws DataAlreadyExists {
    try {
      ApproverEntity saved = approverJpaRepository.save(mapper.approverToApproverEntity(approver));
      return mapper.approverEntityToApprover(saved);
    } catch (DataIntegrityViolationException e) {
      throw new DataAlreadyExists("Approver already exists for id: " + approver.getId().getValue().toString(), e);
    }
  }

}
