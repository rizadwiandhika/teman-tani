package com.temantani.project.service.domain.jpa;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @Entity
// @Table(name = "investments")
public class InvestmentEntity {
  @Id
  private UUID id;

  private String status;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private ProjectEntity project;

  // Other post fields and relationships

  // Getters and setters
}