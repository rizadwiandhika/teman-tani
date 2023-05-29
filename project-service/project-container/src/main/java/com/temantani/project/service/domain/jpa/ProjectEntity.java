package com.temantani.project.service.domain.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Entity
@Table(name = "projects")
public class ProjectEntity {
  @Id
  private UUID id;

  private String status;

  // Other user fields and relationships

  @OneToMany(mappedBy = "project")
  private List<InvestmentEntity> investments;

  // Getters and setters
}
