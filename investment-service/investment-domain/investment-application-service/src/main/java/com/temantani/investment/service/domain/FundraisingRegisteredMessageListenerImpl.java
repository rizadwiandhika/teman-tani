package com.temantani.investment.service.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.ports.input.message.FundraisingRegisteredMessageListener;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingRepository;

@Component
public class FundraisingRegisteredMessageListenerImpl implements FundraisingRegisteredMessageListener {

  private final FundraisingRepository fundraisingRepository;

  public FundraisingRegisteredMessageListenerImpl(FundraisingRepository projectRepository) {
    this.fundraisingRepository = projectRepository;
  }

  @Override
  @Transactional
  public void registerFundraisingProject(UUID projectId, String description, Money fundraisingTarget,
      ZonedDateTime fundraisingDeadline) {
    Fundraising newFundraising = Fundraising.registerNewFundraising(new ProjectId(projectId), description,
        fundraisingTarget,
        fundraisingDeadline);
    fundraisingRepository.create(newFundraising);
  }

}
