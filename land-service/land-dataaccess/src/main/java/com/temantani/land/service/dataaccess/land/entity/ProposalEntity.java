package com.temantani.land.service.dataaccess.land.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proposals")
public class ProposalEntity {

  @Id
  private UUID id;

  private String revisionMessages;
  private String failureMessages;
  private ZonedDateTime approvedAt;
  private ZonedDateTime proposedAt;

}
