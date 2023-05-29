package com.temantani.project.service.domain.entity;

import static com.temantani.domain.DomainConstant.TIMEZONE;
import static com.temantani.domain.helper.Helper.validateNonEmpty;
import static com.temantani.domain.helper.Helper.validateNonNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.temantani.domain.entity.BaseEntity;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.valueobject.ExpenseId;

public class Expense extends BaseEntity<ExpenseId> {

  private ProjectId projectId;
  private ZonedDateTime createdAt;

  private final String name;
  private final String description;
  private final String invoiceUrl;
  private final Money amount;

  public Boolean isValid() {
    return validateNonNull(amount) && amount.isGreaterThanZero()
        && validateNonEmpty(name, description, invoiceUrl);
  }

  public void initiate(ProjectId projectId, ExpenseId expenseId) {
    super.setId(expenseId);
    this.projectId = projectId;
    createdAt = ZonedDateTime.now(ZoneId.of(TIMEZONE));
  }

  public ProjectId getProjectId() {
    return projectId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getInvoiceUrl() {
    return invoiceUrl;
  }

  public Money getAmount() {
    return amount;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  private Expense(Builder builder) {
    super.setId(builder.id);
    this.projectId = builder.projectId;
    this.name = builder.name;
    this.description = builder.description;
    this.invoiceUrl = builder.invoiceUrl;
    this.amount = builder.amount;
    this.createdAt = builder.createdAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  // Builder of Expense
  public static class Builder {
    private ExpenseId id;
    private ProjectId projectId;
    private String name;
    private String description;
    private String invoiceUrl;
    private Money amount;
    private ZonedDateTime createdAt;

    public Builder id(ExpenseId id) {
      this.id = id;
      return this;
    }

    public Builder projectId(ProjectId projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder invoiceUrl(String invoiceUrl) {
      this.invoiceUrl = invoiceUrl;
      return this;
    }

    public Builder amount(Money amount) {
      this.amount = amount;
      return this;
    }

    public Builder createdAt(ZonedDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder() {
    }

    public Expense build() {
      return new Expense(this);
    }
  }

}
