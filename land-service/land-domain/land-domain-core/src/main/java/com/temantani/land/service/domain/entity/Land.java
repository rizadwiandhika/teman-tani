package com.temantani.land.service.domain.entity;

import static com.temantani.domain.DomainConstant.TIMEZONE;
import static com.temantani.land.service.domain.valueobject.LandStatus.AVAILABLE;
import static com.temantani.land.service.domain.valueobject.LandStatus.PROPOSED;
import static com.temantani.land.service.domain.valueobject.LandStatus.REQUIRES_CLEANING;
import static com.temantani.land.service.domain.valueobject.LandStatus.REVISED;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.valueobject.Area;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.LandStatus;
import com.temantani.land.service.domain.valueobject.Proposal;

public class Land extends AggregateRoot<LandId> {

  private Proposal proposal;

  private UserId ownerId;
  private Approver approver;
  private Assessment assessment;
  private LandStatus landStatus;
  private Area area;
  private Address address;
  private String certificateUrl;
  private List<String> photos;

  public static final String PHOTO_DELIMITER = ",";

  // Core business logics
  public void validateProposal(Borrower borrower) {
    validateMandatoryAttributes();

    if (borrower == null || borrower.getId() == null || borrower.getId().getValue().toString().isBlank()) {
      throw new LandDomainException("There should be a borrower for this proposal");
    }

    if (landStatus != null) {
      throw new LandDomainException("Land status is not valid for proposal: " + landStatus.toString());
    }

  }

  public void initiateProposal(UserId borrowerId) {
    super.setId(new LandId(UUID.randomUUID()));
    proposal = Proposal.builder().proposedAt(ZonedDateTime.now(ZoneId.of(TIMEZONE))).build();
    landStatus = PROPOSED;
    ownerId = borrowerId;
  }

  public void validateRevise(Borrower borrower) {
    validateMandatoryAttributes();

    if (borrower == null || borrower.getId().equals(ownerId) == false) {
      throw new LandDomainException("User is not authorized to revise this land");
    }

    if (getId() == null || getId().getValue().toString().isBlank()) {
      throw new LandDomainException("Cannot revise land without initial proposal");
    }

    List<LandStatus> invalidLandStatusForRevise = List.of(REQUIRES_CLEANING, AVAILABLE,
        LandStatus.REJECTED, LandStatus.CANCELED);
    if (invalidLandStatusForRevise.contains(landStatus)) {
      throw new LandDomainException("This land caannot be revised since the status is: " + landStatus.toString());
    }

  }

  public void revise() {
    proposal = Proposal.builder()
        .proposedAt(proposal.getProposedAt())
        .revisionMessages(proposal.getRevisionMessages())
        .build();

    landStatus = REVISED;
  }

  public void approve(Approver approver, Assessment assessment, LandStatus approvalStatus) {
    validateMandatoryAttributes();

    if (landStatus != PROPOSED && landStatus != REVISED) {
      throw new LandDomainException("Land status is not valid for approval: " +
          landStatus.toString());
    }

    if (approvalStatus != AVAILABLE && approvalStatus != REQUIRES_CLEANING) {
      throw new LandDomainException("Approval status is not valid for: " + approvalStatus.toString());
    }

    this.assessment = assessment;
    this.approver = approver;
    landStatus = approvalStatus;
    proposal = Proposal.builder()
        .proposedAt(proposal.getProposedAt())
        .approvedAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)))
        .build();
  }

  public void cancel(Borrower borrower) {
    if (borrower == null || borrower.getId().equals(ownerId) == false) {
      throw new LandDomainException("User is not authorized to cancel this land");
    }

    if (getId() == null || getId().getValue().toString().isBlank()) {
      throw new LandDomainException("Cannot cancel land without initial proposal");
    }

    List<LandStatus> validStateForCancelation = List.of(PROPOSED, LandStatus.REQUIRES_REVISION,
        REVISED);
    if (validStateForCancelation.contains(landStatus) == false) {
      throw new LandDomainException("This land cannot be canceled since the status is: " + landStatus.toString());
    }

    landStatus = LandStatus.CANCELED;
  }

  public void reject(Approver approver, List<String> reasons) {
    if (approver == null || approver.getId() == null || approver.getId().getValue().toString().isBlank()) {
      throw new LandDomainException("There should be an approver for this rejection");
    }

    if (reasons == null || reasons.isEmpty()) {
      throw new LandDomainException("There should be at least 1 failure message");
    }

    if (landStatus != PROPOSED && landStatus != REVISED) {
      throw new LandDomainException("Land status is not valid for rejection: " + landStatus.toString());
    }

    this.approver = approver;
    landStatus = LandStatus.REJECTED;
    proposal = Proposal.builder()
        .failureMessages(reasons)
        .proposedAt(proposal.getProposedAt())
        .approvedAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)))
        .build();
  }

  public void markRevision(Approver approver, List<String> revisionMessages) {
    if (approver == null || approver.getId() == null || approver.getId().getValue().toString().isBlank()) {
      throw new LandDomainException("There should be an approver for this revision mark");
    }

    if (revisionMessages == null || revisionMessages.isEmpty()) {
      throw new LandDomainException("There should be at least 1 revision message");
    }

    if (landStatus != PROPOSED && landStatus != REVISED) {
      throw new LandDomainException("Land status is not valid for mark revision: " + landStatus.toString());
    }

    this.approver = approver;
    landStatus = LandStatus.REQUIRES_REVISION;
    proposal = Proposal.builder()
        .revisionMessages(revisionMessages)
        .proposedAt(proposal.getProposedAt())
        .build();
  }

  // Utilities
  private void validateMandatoryAttributes() {
    if (address.isComplete() == false) {
      throw new LandDomainException("Land address should be complete");
    }

    if (area.getValueInHectare().compareTo(BigDecimal.valueOf(10.0)) < 0) {
      throw new LandDomainException("Land area should be at least 10 hectares");
    }

    if (photos == null || photos.isEmpty()) {
      throw new LandDomainException("Land photos should be at least 1");
    }

    if (certificateUrl == null || certificateUrl.trim().isEmpty()) {
      throw new LandDomainException("Missing land certificate");
    }
  }

  // Constructor, getters, setter
  private Land(Builder builder) {
    super.setId(builder.landId);
    this.ownerId = builder.ownerId;
    this.proposal = builder.proposal;
    this.approver = builder.approver;
    this.assessment = builder.assessment;
    this.area = builder.area;
    this.address = builder.address;
    this.landStatus = builder.landStatus;
    this.certificateUrl = builder.certificateUrl;
    this.photos = builder.photos;
  }

  public UserId getOwnerId() {
    return ownerId;
  }

  public Proposal getProposal() {
    return proposal;
  }

  public Approver getApprover() {
    return approver;
  }

  public Assessment getAssessment() {
    return assessment;
  }

  public Area getArea() {
    return area;
  }

  public Address getAddress() {
    return address;
  }

  public LandStatus getLandStatus() {
    return landStatus;
  }

  public String getCertificateUrl() {
    return certificateUrl;
  }

  public List<String> getPhotos() {
    return photos;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setCertificateUrl(String certificateUrl) {
    this.certificateUrl = certificateUrl;
  }

  public void setPhotos(List<String> photos) {
    this.photos = photos;
  }

  public static Builder builder() {
    return new Builder();
  }

  // Builder fot this Land class
  public static class Builder {
    LandId landId;
    UserId ownerId;
    Proposal proposal;

    Approver approver;
    Assessment assessment;
    Area area;
    Address address;
    LandStatus landStatus;
    String certificateUrl;
    List<String> photos;

    private Builder() {
    }

    public Builder landId(LandId landId) {
      this.landId = landId;
      return this;
    }

    public Builder ownerId(UserId ownerId) {
      this.ownerId = ownerId;
      return this;
    }

    public Builder proposal(Proposal proposal) {
      this.proposal = proposal;
      return this;
    }

    public Builder approver(Approver approver) {
      this.approver = approver;
      return this;
    }

    public Builder assessment(Assessment assessment) {
      this.assessment = assessment;
      return this;
    }

    public Builder area(Area area) {
      this.area = area;
      return this;
    }

    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    public Builder landStatus(LandStatus landStatus) {
      this.landStatus = landStatus;
      return this;
    }

    public Builder certificateUrl(String certificateUrl) {
      this.certificateUrl = certificateUrl;
      return this;
    }

    public Builder photos(List<String> photos) {
      this.photos = photos;
      return this;
    }

    public Land build() {
      return new Land(this);
    }
  }

}