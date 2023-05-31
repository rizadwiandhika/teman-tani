package com.temantani.project.service.domain.ports.output.repository;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.entity.Investment;

public interface InvestmentRepository {

  Investment create(Investment investment) throws DataAlreadyExistsException;

}
