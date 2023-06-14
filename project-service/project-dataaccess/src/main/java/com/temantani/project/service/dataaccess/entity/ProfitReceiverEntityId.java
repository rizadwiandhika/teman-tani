package com.temantani.project.service.dataaccess.entity;

import java.io.Serializable;
import java.util.UUID;

import com.temantani.project.service.domain.valueobject.DistributionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitReceiverEntityId implements Serializable {

  private DistributionType type;
  private ProjectEntity project;
  private UUID receiverId;

}
