package com.temantani.land.service.dataaccess.land.mapper;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.land.entity.ApproverEntity;
import com.temantani.land.service.domain.entity.Approver;

@Component
public class ApproverDataAccessMapper {

  public ApproverEntity approverToApproverEntity(Approver approver) {
    return ApproverEntity.builder()
        .id(approver.getId().getValue())
        .email(approver.getEmail())
        .name(approver.getName())
        .build();
  }

  public Approver approverEntityToApprover(ApproverEntity approverEntity) {
    return Approver.builder()
        .approverId(new UserId(approverEntity.getId()))
        .email(approverEntity.getEmail())
        .name(approverEntity.getName())
        .build();
  }

}
