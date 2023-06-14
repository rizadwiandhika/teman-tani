package com.temantani.project.service.domain.ports.input.message.listener;

import java.util.List;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.entity.Investment;

public interface CloseFundraisingResponseMessageListener {

  void proceededToHiring(ProjectId projectId, List<Investment> investments);

}
