package com.temantani.investment.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.exception.InvestorAlreadyExistsException;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.ports.input.message.UserMessageListener;
import com.temantani.investment.service.domain.ports.output.repository.InvestorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserMessageListenerImpl implements UserMessageListener {

  private final InvestorRepository investorRepository;
  private final InvestmentDataMapper mapper;

  public UserMessageListenerImpl(InvestorRepository investorRepository, InvestmentDataMapper mapper) {
    this.investorRepository = investorRepository;
    this.mapper = mapper;
  }

  @Override
  @Transactional
  public void createInvestor(CreateInvestorMessage createInvestorMessage) {
    Investor investor = mapper.createInvestorMessageToInvestor(createInvestorMessage);

    investor = investorRepository.save(investor);
    if (investor == null) {
      log.error("Unable to persist investor for id: {}", createInvestorMessage.getUserId().toString());
      throw new InvestmentDomainException("Unable to persist investor");
    }
  }

}
