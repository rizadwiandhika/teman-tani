package com.temantani.investment.service.domain.ports.input.message;

import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;

public interface UserMessageListener {

  void createInvestor(CreateInvestorMessage createInvestorMessage);

}
