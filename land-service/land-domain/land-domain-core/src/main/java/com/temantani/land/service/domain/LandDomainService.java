package com.temantani.land.service.domain;

import java.util.List;

import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.event.LandApprovedEvent;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.LandStatus;

public interface LandDomainService {

  void proposeLand(Land land, Borrower borrower);

  void markAsRevision(Land land, Approver approver, List<String> revisionMessages);

  void revise(Land land, Borrower borrower);

  LandApprovedEvent approve(Land land, Approver approver, Assessment Assesment, LandStatus approvalStatus);

  void reject(Land land, Approver approver, List<String> failureMessages);

  void cancel(Land land, Borrower borrower);

}
