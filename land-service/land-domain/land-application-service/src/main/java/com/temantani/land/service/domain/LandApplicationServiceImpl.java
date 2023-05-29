package com.temantani.land.service.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.approval.ApprovalRequest;
import com.temantani.land.service.domain.dto.proposal.ProposalRequest;
import com.temantani.land.service.domain.dto.revision.RevisionRequest;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.event.LandApprovedEvent;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.mapper.LandDataMapper;
import com.temantani.land.service.domain.outbox.scheduler.LandOutboxHelper;
import com.temantani.land.service.domain.ports.input.service.LandApplicationService;
import com.temantani.land.service.domain.ports.output.repository.ApproverRepository;
import com.temantani.land.service.domain.ports.output.repository.BorrowerRepository;
import com.temantani.land.service.domain.ports.output.repository.LandRepository;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.LandStatus;

@Service
public class LandApplicationServiceImpl implements LandApplicationService {

  private final LandDataMapper mapper;
  private final LandDomainService domainService;
  private final BorrowerRepository borrowerRepository;
  private final LandRepository landRepository;
  private final ApproverRepository approverRepository;
  private final LandOutboxHelper landOutboxHelper;

  public LandApplicationServiceImpl(LandDataMapper mapper, LandDomainService domainService,
      BorrowerRepository borrowerRepository, LandRepository landRepository, ApproverRepository approverRepository,
      LandOutboxHelper landOutboxHelper) {
    this.mapper = mapper;
    this.domainService = domainService;
    this.borrowerRepository = borrowerRepository;
    this.landRepository = landRepository;
    this.approverRepository = approverRepository;
    this.landOutboxHelper = landOutboxHelper;
  }

  @Override
  @Transactional
  public BasicResponse createProposal(ProposalRequest proposal) {
    Borrower borrower = findBorrowerByIdOrThrow(proposal.getBorrowerId());
    Land land = mapper.proposalRequestToLand(proposal);

    domainService.proposeLand(land, borrower);

    landRepository.save(land);

    return BasicResponse.builder().message("Land prpoposed successfully").build();
  }

  @Override
  @Transactional
  public BasicResponse markRevision(List<String> revisionMessages, UserId approverId, LandId landId) {
    Approver approver = findApproverByIdOrThrow(approverId);
    Land land = findLandByIdOrThrow(landId);

    domainService.markAsRevision(land, approver, revisionMessages);

    landRepository.save(land);

    return BasicResponse.builder().message("Land successfully marked as requires revision").build();
  }

  @Override
  @Transactional
  public BasicResponse revise(RevisionRequest request, UserId borrowerId) {
    Land land = findLandByIdOrThrow(request.getLandId());
    Borrower borrower = findBorrowerByIdOrThrow(borrowerId);

    land.setAddress(mapper.adrressDtoToAddress(request.getAddress()));
    land.setArea(mapper.areaDtoToArea(request.getArea()));
    land.setCertificateUrl(request.getCertificateUrl());
    land.setPhotos(new ArrayList<>(Arrays.asList(request.getPhotoUrl())));

    domainService.revise(land, borrower);
    landRepository.save(land);

    return BasicResponse.builder().message("Land successfully revised").build();
  }

  @Override
  @Transactional
  public BasicResponse cancel(LandId landId, UserId borrowerId) {
    Land land = findLandByIdOrThrow(landId);
    Borrower borrower = findBorrowerByIdOrThrow(borrowerId);

    domainService.cancel(land, borrower);
    landRepository.save(land);

    return BasicResponse.builder().message("Land successfully cancelled").build();
  }

  @Override
  @Transactional
  public BasicResponse reject(LandId landId, UserId approverId, List<String> reasons) {
    Land land = findLandByIdOrThrow(landId);
    Approver approver = findApproverByIdOrThrow(approverId);

    domainService.reject(land, approver, reasons);
    landRepository.save(land);

    return BasicResponse.builder().message("Land successfully rejected").build();
  }

  @Override
  @Transactional
  public BasicResponse approve(LandId landId, UserId approverId, ApprovalRequest request) {
    Land land = findLandByIdOrThrow(landId);
    Approver approver = findApproverByIdOrThrow(approverId);
    Assessment assesment = mapper.approvalRequestToAssesment(request);
    LandStatus approvalStatus;

    try {
      approvalStatus = LandStatus.valueOf(request.getApprovalStatus());
    } catch (Exception e) {
      throw new LandDomainException("Invalid approval status: " + request.getApprovalStatus(), e);
    }

    LandApprovedEvent domainEvent = domainService.approve(land, approver, assesment, approvalStatus);

    landRepository.save(land);
    landOutboxHelper.createOutboxMessage(mapper.landApprovedEventToLandApprovedEventMessage(domainEvent));

    return BasicResponse.builder().message("Land successfully approved").build();
  }

  private Borrower findBorrowerByIdOrThrow(UserId borrowerId) {
    return borrowerRepository.findById(borrowerId)
        .orElseThrow(() -> new LandDomainException("Borrower id cannot be found: " + borrowerId.getValue().toString()));
  }

  private Approver findApproverByIdOrThrow(UserId approverId) {
    return approverRepository.findById(approverId)
        .orElseThrow(() -> new LandDomainException("Approver id cannot be found: " + approverId.getValue().toString()));
  }

  private Land findLandByIdOrThrow(LandId landId) {
    return landRepository.findById(landId)
        .orElseThrow(() -> new LandDomainException("Land id cannot be found: " + landId.getValue().toString()));
  }

}
