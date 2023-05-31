package com.temantani.investment.service.dataaccess.postgresql.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.temantani.investment.service.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {

  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  private ProjectStatus status;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<InvestmentEntity> investments;

  private BigDecimal fundraisingTarget;
  private BigDecimal collectedFunds;
  private BigDecimal bookedFunds;
  private String description;
  private ZonedDateTime tenorDeadline;
  private ZonedDateTime createdAt;

  @Version
  private Integer version;
}
