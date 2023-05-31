package com.temantani.project.service.domain.ports.input.message.listener;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.dto.message.investment.InvestmentPaidMessage;
import com.temantani.project.service.domain.exception.ProjectDomainException;

public interface InvestmentPaidMessageListener {

  void addInvestmentToProject(InvestmentPaidMessage message) throws DataAlreadyExistsException, ProjectDomainException;

}
