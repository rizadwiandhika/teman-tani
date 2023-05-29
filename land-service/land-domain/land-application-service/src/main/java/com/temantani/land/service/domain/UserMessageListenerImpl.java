package com.temantani.land.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.land.service.domain.dto.message.CreateApproverMessage;
import com.temantani.land.service.domain.dto.message.CreateBorrowerMessage;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.mapper.LandDataMapper;
import com.temantani.land.service.domain.ports.input.message.UserMessageListener;
import com.temantani.land.service.domain.ports.output.repository.ApproverRepository;
import com.temantani.land.service.domain.ports.output.repository.BorrowerRepository;

@Component
public class UserMessageListenerImpl implements UserMessageListener {

  private final BorrowerRepository borrowerRepository;
  private final ApproverRepository approverRepository;
  private final LandDataMapper mapper;

  public UserMessageListenerImpl(BorrowerRepository borrowerRepository, ApproverRepository approverRepository,
      LandDataMapper mapper) {
    this.borrowerRepository = borrowerRepository;
    this.approverRepository = approverRepository;
    this.mapper = mapper;
  }

  @Override
  @Transactional
  public void createApprover(CreateApproverMessage message) {
    Approver approver = mapper.createApproverMessageToApprover(message);

    approver = approverRepository.save(approver);
    if (approver == null) {
      throw new LandDomainException("Unable to persist approver id: " + message.getApproverId());
    }
  }

  @Override
  @Transactional
  public void createBorrower(CreateBorrowerMessage message) {
    Borrower borrower = mapper.createBorrowerMessageToBorrower(message);

    borrower = borrowerRepository.save(borrower);
    if (borrower == null) {
      throw new LandDomainException("Unable to persist borrower id: " + message.getBorrowerId());
    }
  }

}
