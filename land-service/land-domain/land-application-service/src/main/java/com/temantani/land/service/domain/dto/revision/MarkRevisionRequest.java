package com.temantani.land.service.domain.dto.revision;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkRevisionRequest {
  @Valid
  @NotNull
  private List<String> revisionMessages;
}
