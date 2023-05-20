package com.temantani.land.service.domain.ports.input.service;

import java.util.List;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.approval.ApprovalRequest;
import com.temantani.land.service.domain.dto.proposal.ProposalRequest;
import com.temantani.land.service.domain.dto.revision.RevisionRequest;

public interface LandApplicationService {

  BasicResponse createProposal(ProposalRequest proposalRequest);

  BasicResponse markRevision(List<String> reivisionMessages, UserId approverId, LandId landId);

  BasicResponse revise(RevisionRequest request, UserId borrowerId);

  BasicResponse cancel(LandId landId, UserId userId);

  BasicResponse reject(LandId landId, UserId approverId, List<String> reasons);

  BasicResponse approve(LandId landId, UserId approverId, ApprovalRequest request);
}
