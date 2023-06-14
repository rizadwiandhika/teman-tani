package com.temantani.project.service.dataaccess.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "receivers")
public class ReceiverEntity {

  @Id
  private UUID id;

  @Version
  private Integer version;

  private String bank;
  private String accountNumber;
  private String accountHolderName;

}
