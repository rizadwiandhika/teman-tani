package com.temantani.project.service.dataaccess.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "expenses")
public class ExpenseEntity {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private ProjectEntity project;

  private ZonedDateTime createdAt;
  private String name;
  private String description;
  private String invoiceUrl;
  private BigDecimal amount;

}
