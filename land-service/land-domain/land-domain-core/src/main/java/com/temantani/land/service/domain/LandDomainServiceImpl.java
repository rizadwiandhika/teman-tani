package com.temantani.land.service.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.temantani.domain.DomainConstant;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.event.LandApprovedEvent;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.LandStatus;

public class LandDomainServiceImpl implements LandDomainService {

  @Override
  public LandApprovedEvent approve(Land land, Approver approver, Assessment assessment, LandStatus status) {
    land.approve(approver, assessment, status);
    return new LandApprovedEvent(land, ZonedDateTime.now(ZoneId.of(DomainConstant.TIMEZONE)));
  }

  @Override
  public void cancel(Land land, Borrower borrower) {
    land.cancel(borrower);
  }

  @Override
  public void markAsRevision(Land land, Approver approver, List<String> revisionMessages) {
    land.markRevision(approver, revisionMessages);
  }

  @Override
  public void proposeLand(Land land, Borrower borrower) {
    land.validateProposal(borrower);
    land.initiateProposal(borrower.getId());
  }

  @Override
  public void reject(Land land, Approver approver, List<String> reasons) {
    land.reject(approver, reasons);
  }

  @Override
  public void revise(Land revisedLand, Borrower borrower) {
    revisedLand.validateRevise(borrower);
    revisedLand.revise();
  }

}
