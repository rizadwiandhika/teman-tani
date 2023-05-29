package com.temantani.project.service.domain.ports.output.repository;

import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;

public interface InvestmentRepository {

  Investment create(Investment investment) throws DataAlreadyExistsException;

}
