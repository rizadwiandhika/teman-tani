package com.temantani.investment.service.domain.ports.input.message;

import com.temantani.domain.valueobject.ProjectId;

public interface CloseFundraisingMessageListener {

  void closeFundraising(ProjectId projectId);

}
