package com.temantani.project.service.domain.ports.output.repository;

import java.util.List;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.entity.Investment;

public interface InvestmentRepository {

  Investment create(Investment investment) throws DataAlreadyExistsException;

  List<Investment> saveAll(List<Investment> investments);

}
