package com.temantani.land.service.dataaccess.land.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
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
@Table(name = "approvers")
public class ApproverEntity {

  @Id
  private UUID id;

  private String email;
  private String name;

  @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL)
  private List<LandEntity> lands;

}
