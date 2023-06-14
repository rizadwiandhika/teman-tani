package com.temantani.land.service.domain.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApproverData {

  private String id;
  private String email;
  private String name;

}
