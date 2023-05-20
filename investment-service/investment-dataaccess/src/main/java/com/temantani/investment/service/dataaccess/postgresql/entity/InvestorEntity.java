package com.temantani.investment.service.dataaccess.postgresql.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "investors")
public class InvestorEntity {

  @Id
  private UUID id;
  private String email;
  private String name;
  private String profilePictureUrl;

}
