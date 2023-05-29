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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.ProjectStatus;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.ExpenseId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;
import com.temantani.project.service.domain.valueobject.ProfitReceiver;

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
  private List<ProfitReceiver> profitReceivers;
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

    if (validateNonEmpty(description, harvest)) {
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
  }

  public void createProject(LandId landId, UserId managerId, UserId landOwnerId) {
    super.setId(ProjectId.generate());
    this.landId = landId;
    this.managerId = managerId;

    status = FUNDRAISING;
    createdAt = now();

    profitReceivers = new ArrayList<>();
    // TODO: how much should a land owner takes devidend?
    profitReceivers.add(ProfitReceiver.makeLandowner(landOwnerId));

    if (details == null) {
      details = new HashMap<>();
    }
  }

  public void continueToHiring(UserId managerId) {
    validateManager(managerId);

    if (status != FUNDRAISING) {
      throw new ProjectDomainException("Cannot start hiring since the project is not in FUNDRAISING status");
    }

    if (collectedFunds.isGreaterThanZero() == false) {
      throw new ProjectDomainException("Cannot start hiring since there is no collected funds");
    }

    if (profitReceivers == null || profitReceivers.isEmpty()) {
      throw new ProjectDomainException("Cannot start hiring since there is no profit receivers");
    }

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

  public Investment addInvestment(InvestmentId investmentId, UserId investorId, Money amount) {
    if (status != FUNDRAISING) {
      throw new ProjectDomainException("Cannot add investment since project is not in FUNDRAISING status");
    }

    if (amount.isGreaterThanZero() == false) {
      throw new ProjectDomainException("Cannot add investment with amount is less than zero");
    }

    collectedFunds = collectedFunds.add(amount);
    if (profitReceivers == null) {
      profitReceivers = new ArrayList<>();
    }

    // TODO: reduce with land owner devidend
    Money deviden = amount.divide(collectedFunds);
    profitReceivers.add(ProfitReceiver.makeInvestor(investorId, deviden.getAmount()));

    return Investment.builder().id(investmentId).projectId(getId()).investorId(investorId).amount(amount).build();
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

    if (expenses == null) {
      expenses = new ArrayList<>();
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
    profitReceivers.forEach((r) -> {
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

  public List<ProfitReceiver> getProfitReceivers() {
    return profitReceivers;
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
    this.profitReceivers = builder.profitReceivers;
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
    private List<ProfitReceiver> profitReceivers;
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

    public Builder profitReceivers(List<ProfitReceiver> profitReceivers) {
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
