package com.temantani.land.service.dataaccess.borrower.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "borrowers")
public class BorrowerEntity {

  @Id
  private UUID id;
  private String name;
  private String email;
  private String profilePictureUrl;
}
