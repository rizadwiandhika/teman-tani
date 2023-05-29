package com.temantani.project.service.domain.dto.project;

import java.util.UUID;

import com.temantani.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseProjectResponse {
  private UUID projectId;
  private UUID managerId;
  private UUID landId;
  private ProjectStatus status;
}
