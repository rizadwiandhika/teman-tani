package com.temantani.land.service.domain.ports.input.message;

import com.temantani.land.service.domain.dto.message.CreateApproverMessage;
import com.temantani.land.service.domain.dto.message.CreateBorrowerMessage;

public interface UserMessageListener {

  void createBorrower(CreateBorrowerMessage message);

  void createApprover(CreateApproverMessage message);

}
