package com.temantani.land.service.domain.valueobject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Proposal {
  private final ZonedDateTime approvedAt;
  private final ZonedDateTime proposedAt;
  private final List<String> revisionMessages;
  private final List<String> failureMessages;

  public static final String MESSAGE_DELIMITER = ",";

  private Proposal(Builder builder) {
    this.approvedAt = builder.approvedAt;
    this.proposedAt = builder.proposedAt;
    this.revisionMessages = builder.revisionMessages;
    this.failureMessages = builder.failureMessages;
  }

  public ZonedDateTime getApprovedAt() {
    return approvedAt;
  }

  public ZonedDateTime getProposedAt() {
    return proposedAt;
  }

  public List<String> getRevisionMessages() {
    return revisionMessages;
  }

  public List<String> getFailureMessages() {
    return failureMessages;
  }

  public static Builder builder() {
    return new Builder();
  }

  // Builder
  public static class Builder {
    private ZonedDateTime proposedAt;
    private ZonedDateTime approvedAt;
    private List<String> revisionMessages;
    private List<String> failureMessages;

    public Builder() {
      this.revisionMessages = new ArrayList<>();
      this.failureMessages = new ArrayList<>();
    }

    public Builder proposedAt(ZonedDateTime proposedAt) {
      this.proposedAt = proposedAt;
      return this;
    }

    public Builder approvedAt(ZonedDateTime approvedAt) {
      this.approvedAt = approvedAt;
      return this;
    }

    public Builder revisionMessages(List<String> revisionMessages) {
      this.revisionMessages = revisionMessages;
      return this;
    }

    public Builder failureMessages(List<String> failureMessages) {
      this.failureMessages = failureMessages;
      return this;
    }

    public Proposal build() {
      return new Proposal(this);
    }
  }

}
