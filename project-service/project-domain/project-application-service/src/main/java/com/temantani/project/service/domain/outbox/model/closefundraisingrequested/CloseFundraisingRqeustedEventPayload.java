package com.temantani.project.service.domain.outbox.model.closefundraisingrequested;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CloseFundraisingRqeustedEventPayload {
  @JsonProperty
  private UUID projectId;
}
