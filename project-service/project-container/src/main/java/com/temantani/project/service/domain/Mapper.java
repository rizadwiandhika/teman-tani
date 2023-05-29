package com.temantani.project.service.domain;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.project.service.domain.dto.InvestmentObject;
import com.temantani.project.service.domain.dto.ProjectObject;
import com.temantani.project.service.domain.jpa.InvestmentEntity;
import com.temantani.project.service.domain.jpa.ProjectEntity;

@Component
public class Mapper {

  public ProjectObject projectEntityToProjectObject(ProjectEntity entity) {
    return ProjectObject.builder()
        .id(entity.getId())
        .status(entity.getStatus())
        .investments(
            entity.getInvestments().stream().map(this::investmentEntityToInvestmentObject).collect(Collectors.toList()))
        .build();
  }

  public InvestmentObject investmentEntityToInvestmentObject(InvestmentEntity entity) {
    if (entity == null) {
      return null;
    }

    return InvestmentObject.builder()
        .id(entity.getId())
        .projectId(entity.getProject().getId())
        .status(entity.getStatus())
        .build();
  }

}
