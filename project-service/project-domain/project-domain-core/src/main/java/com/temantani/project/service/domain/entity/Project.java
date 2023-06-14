package com.temantani.project.service.domain.entity;

import static com.temantani.domain.helper.Helper.now;
import static com.temantani.domain.helper.Helper.validateNonEmpty;
import static com.temantani.domain.helper.Helper.validateNonNull;
import static com.temantani.domain.valueobject.ProjectStatus.CANCELED;
import static com.temantani.domain.valueobject.ProjectStatus.FINISHED;
import static com.temantani.domain.valueobject.ProjectStatus.FUNDRAISING;
import static com.temantani.domain.valueobject.ProjectStatus.HIRING;
import static com.temantani.domain.valueobject.ProjectStatus.ONGOING;
import static com.temantani.project.service.domain.valueobject.DistributionStatus.WAITING;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.ProjectStatus;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProceededHiringException;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.ExpenseId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

public class Project extends AggregateRoot<ProjectId> {

  private LandId landId;
  private UserId managerId;
  private String description;
  private Map<String, String> details;
  private String harvest;
  private ProjectStatus status;
  private Integer workerNeeds;
  private Money fundraisingTarget;
  private ZonedDateTime fundraisingDeadline;
  private ZonedDateTime estimatedFinished;
  private Money collectedFunds;
  private Money income;
  private Money distributedIncome;
  private List<ShareHolder> shareHolders;
  private List<Expense> expenses;
  private ZonedDateTime createdAt;
  private ZonedDateTime executedAt;
  private ZonedDateTime finishedAt;
  private List<String> failureMessages;

  public static final String FAILURE_MESSAGES_DELIMITER = ";";

  /**
   * Core business logics
   */
  public void validateProjectInitialization() {
    if (getId() != null || status != null || createdAt != null) {
      throw new ProjectDomainException(
          "Project is not in correct state for initialization. Project id, status, and created at should be empty initially");
    }

    if (validateNonNull(landId, description, harvest, workerNeeds, fundraisingTarget, fundraisingDeadline,
        estimatedFinished) == false) {
      throw new ProjectDomainException("Invalid required project plan information for initialization");
    }

    if (validateNonEmpty(description, harvest) == false) {
      throw new ProjectDomainException("Description and harvest information should not be empty");
    }

    if (workerNeeds < 0) {
      throw new ProjectDomainException("Invalid number of worker needs");
    }

    if (fundraisingTarget.isGreaterThanZero() == false) {
      throw new ProjectDomainException("Fundraising target cannot be negative");
    }

    if (fundraisingDeadline.isAfter(estimatedFinished)) {
      throw new ProjectDomainException("Fundraising deadline cannot be behind the estimated project finish date");
    }

    if (fundraisingDeadline.isBefore(Helper.now())) {
      throw new ProjectDomainException("Fundraising deadline cannot be behind current date");
    }
  }

  public void createProject(LandId landId, UserId managerId, UserId landOwnerId) {
    super.setId(ProjectId.generate());
    this.landId = landId;
    this.managerId = managerId;

    status = FUNDRAISING;
    createdAt = now();
    collectedFunds = Money.ZERO;
    income = Money.ZERO;
    distributedIncome = Money.ZERO;
    failureMessages = new ArrayList<>();
    shareHolders = new ArrayList<>();
    expenses = new ArrayList<>();

    shareHolders = new ArrayList<>();
    // TODO: how much should a land owner takes devidend?
    shareHolders.add(ShareHolder.makeLandowner(landOwnerId));

    if (details == null) {
      details = new HashMap<>();
    }
  }

  public void initiateToHiring(UserId managerId) {
    validateManager(managerId);

    if (status != FUNDRAISING) {
      throw new ProjectDomainException("Cannot start hiring since the project is not in FUNDRAISING status");
    }

    status = ProjectStatus.WAITING_FOR_FUNDS;
  }

  public void proceededToHiring(List<Investment> investments) {
    if (status != ProjectStatus.WAITING_FOR_FUNDS) {
      throw new ProceededHiringException("Cannot proceeded to hiring since the project status is: " + status.name());
    }

    if (investments == null || investments.isEmpty()) {
      throw new ProjectDomainException("There is no investments for this project: " + getId().getValue());
    }

    shareHolders = new ArrayList<>();
    collectedFunds = investments.stream().map(Investment::getAmount).reduce(Money.ZERO, Money::add);
    if (collectedFunds.isGreaterThanZero() == false) {
      throw new ProjectDomainException("Cannot start hiring since there is no collected funds");
    }

    investments.forEach(i -> {
      if (i.getProjectId().equals(getId()) == false) {
        throw new ProjectDomainException(
            "Investment: " + i.getId().getValue() + " does not belong to project: " + getId().getValue());
      }

      if (i.getAmount().isGreaterThanZero() == false) {
        throw new ProjectDomainException("Investment cannot be zero or less");
      }

      BigDecimal deviden = i.getAmount().divide(collectedFunds);
      shareHolders.add(ShareHolder.makeInvestor(i.getInvestorId(), deviden));
    });

    status = HIRING;
  }

  public void execute(UserId managerId) {
    validateManager(managerId);

    if (status != HIRING) {
      throw new ProjectDomainException("Cannot execute project because project is not in HIRING status");
    }

    status = ONGOING;
    executedAt = now();
  }

  public void finish(UserId managerId) {
    validateManager(managerId);

    if (status != ONGOING) {
      throw new ProjectDomainException("Cannot finish project because project is not in ONGOING status");
    }

    status = FINISHED;
    finishedAt = now();
  }

  public void cancel(UserId managerId, List<String> reasons) {
    validateManager(managerId);

    if (status == ONGOING || status == FINISHED || status == CANCELED) {
      throw new ProjectDomainException("Project that is already ONGOING, FINISHED, or CANCELED cannot be canceled");
    }

    if (reasons == null || reasons.isEmpty()) {
      throw new ProjectDomainException("Project cancelation needs reasons");
    }

    status = CANCELED;
    failureMessages = new ArrayList<>(reasons);
  }

  public void addExpense(UserId managerId, Expense expense) {
    validateManager(managerId);

    if (status != ONGOING) {
      throw new ProjectDomainException("Cannot add expense since project is not in ONGOING status");
    }

    if (expense == null) {
      throw new ProjectDomainException("Cannot add null expense");
    }

    Money totalExpense = expenses.stream().map(Expense::getAmount).reduce(Money.ZERO, Money::add);
    totalExpense = totalExpense.add(expense.getAmount());

    if (totalExpense.isGreaterThan(collectedFunds)) {
      throw new ProjectDomainException("Cannot add expense since expense amount is greater than collected funds");
    }

    expense.initiate(getId(), ExpenseId.generate());
    expenses.add(expense);
  }

  public void addIncome(Money income) {
    if (status != FINISHED) {
      throw new ProjectDomainException("Cannot add income since project is not in FINISHED status");
    }

    if (income == null) {
      throw new ProjectDomainException("Cannot add null income");
    }

    this.income = this.income.add(income);
  }

  public ProfitDistribution initiateProfitDistribution(UserId managUserId, Map<UserId, BankAccount> receiverBank) {
    validateManager(managUserId);

    if (status != FINISHED) {
      throw new ProjectDomainException("Cannot distribute profit since project is not in FINISHED status");
    }

    if (receiverBank == null) {
      throw new ProjectDomainException("Cannot distribute profit since receiver bank is null");
    }

    Money profit = income.substract(distributedIncome);
    if (profit.isGreaterThanZero() == false) {
      throw new ProjectDomainException("Project profit must be greater than zero to do profit distribution");
    }
    distributedIncome = income;

    ProfitDistributionId profitDistributionId = ProfitDistributionId.generate();

    List<ProfitDistributionDetail> distributionDetails = new ArrayList<>();
    shareHolders.forEach((r) -> {
      distributionDetails.add(ProfitDistributionDetail.builder()
          .id(ProfitDistributionDetailId.generate())
          .profitDistributionId(profitDistributionId)
          .bankAccount(receiverBank.get(r.getReceiverId()))
          .receiver(r)
          .amount(profit.multiply(r.getDevidend()))
          .build());
    });

    return ProfitDistribution.builder()
        .id(profitDistributionId)
        .managerId(managUserId)
        .projectId(getId())
        .projectProfit(profit)
        .details(distributionDetails)
        .status(WAITING)
        .build();
  }

  private void validateManager(UserId managerId) {
    if (this.managerId.equals(managerId) == false) {
      throw new ProjectDomainException("Only manager of this project can manage this project");
    }
  }

  /**
   * Getter, Builder Constructor
   */

  public LandId getLandId() {
    return landId;
  }

  public UserId getManagerId() {
    return managerId;
  }

  public String getDescription() {
    return description;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public String getHarvest() {
    return harvest;
  }

  public ProjectStatus getStatus() {
    return status;
  }

  public Integer getWorkerNeeds() {
    return workerNeeds;
  }

  public Money getFundraisingTarget() {
    return fundraisingTarget;
  }

  public ZonedDateTime getFundraisingDeadline() {
    return fundraisingDeadline;
  }

  public ZonedDateTime getEstimatedFinished() {
    return estimatedFinished;
  }

  public Money getCollectedFunds() {
    return collectedFunds;
  }

  public Money getIncome() {
    return income;
  }

  public Money getDistributedIncome() {
    return distributedIncome;
  }

  public List<ShareHolder> getShareHolders() {
    return shareHolders;
  }

  public List<Expense> getExpenses() {
    return expenses;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public ZonedDateTime getExecutedAt() {
    return executedAt;
  }

  public ZonedDateTime getFinishedAt() {
    return finishedAt;
  }

  public List<String> getFailureMessages() {
    return failureMessages;
  }

  private Project(Builder builder) {
    super.setVersion(builder.version);
    super.setId(builder.id);

    this.landId = builder.landId;
    this.managerId = builder.managerId;
    this.description = builder.description;
    this.details = builder.details;
    this.harvest = builder.harvest;
    this.status = builder.status;
    this.workerNeeds = builder.workerNeeds;
    this.fundraisingTarget = builder.fundraisingTarget;
    this.fundraisingDeadline = builder.fundraisingDeadline;
    this.estimatedFinished = builder.estimatedFinished;
    this.collectedFunds = builder.collectedFunds;
    this.income = builder.income;
    this.distributedIncome = builder.distributedIncome;
    this.shareHolders = builder.profitReceivers;
    this.expenses = builder.expenses;
    this.createdAt = builder.createdAt;
    this.executedAt = builder.executedAt;
    this.finishedAt = builder.finishedAt;
    this.failureMessages = builder.failureMessages;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Integer version;
    private ProjectId id;
    private LandId landId;
    private UserId managerId;
    private String description;
    private Map<String, String> details;
    private String harvest;
    private ProjectStatus status;
    private Integer workerNeeds;
    private Money fundraisingTarget;
    private ZonedDateTime fundraisingDeadline;
    private ZonedDateTime estimatedFinished;
    private Money collectedFunds;
    private Money income;
    private Money distributedIncome;
    private List<ShareHolder> profitReceivers;
    private List<Expense> expenses;
    private ZonedDateTime createdAt;
    private ZonedDateTime executedAt;
    private ZonedDateTime finishedAt;

    private List<String> failureMessages;

    public Builder version(Integer version) {
      this.version = version;
      return this;
    }

    public Builder id(ProjectId id) {
      this.id = id;
      return this;
    }

    public Builder landId(LandId landId) {
      this.landId = landId;
      return this;
    }

    public Builder managerId(UserId managerId) {
      this.managerId = managerId;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder details(Map<String, String> details) {
      this.details = details;
      return this;
    }

    public Builder harvest(String harvest) {
      this.harvest = harvest;
      return this;
    }

    public Builder status(ProjectStatus status) {
      this.status = status;
      return this;
    }

    public Builder workerNeeds(Integer workerNeeds) {
      this.workerNeeds = workerNeeds;
      return this;
    }

    public Builder fundraisingTarget(Money fundraisingTarget) {
      this.fundraisingTarget = fundraisingTarget;
      return this;
    }

    public Builder fundraisingDeadline(ZonedDateTime fundraisingDeadline) {
      this.fundraisingDeadline = fundraisingDeadline;
      return this;
    }

    public Builder estimatedFinished(ZonedDateTime estimatedFinished) {
      this.estimatedFinished = estimatedFinished;
      return this;
    }

    public Builder collectedFunds(Money collectedFunds) {
      this.collectedFunds = collectedFunds;
      return this;
    }

    public Builder income(Money income) {
      this.income = income;
      return this;
    }

    public Builder distributedIncome(Money distributedIncome) {
      this.distributedIncome = distributedIncome;
      return this;
    }

    public Builder profitReceivers(List<ShareHolder> profitReceivers) {
      this.profitReceivers = profitReceivers;
      return this;
    }

    public Builder expenses(List<Expense> expenses) {
      this.expenses = expenses;
      return this;
    }

    public Builder createdAt(ZonedDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder executedAt(ZonedDateTime executedAt) {
      this.executedAt = executedAt;
      return this;
    }

    public Builder finishedAt(ZonedDateTime finishedAt) {
      this.finishedAt = finishedAt;
      return this;
    }

    public Builder failureMessages(List<String> failureMessages) {
      this.failureMessages = failureMessages;
      return this;
    }

    public Project build() {
      return new Project(this);
    }

  }

}
