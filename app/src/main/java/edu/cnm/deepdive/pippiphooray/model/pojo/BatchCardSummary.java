package edu.cnm.deepdive.pippiphooray.model.pojo;

import java.time.LocalDate;

public class BatchCardSummary {

  private final long batchId;
  private final Integer batchNumber;
  private final String incubatorName;
  private final String breedSummary;
  private final int numEggsSet;
  private final LocalDate expectedHatchDate;
  private final String nextMilestoneLabel;
  private final LocalDate nextMilestoneDate;

  public BatchCardSummary(
      long batchId,
      Integer batchNumber,
      String incubatorName,
      String breedSummary,
      int numEggsSet,
      LocalDate expectedHatchDate,
      String nextMilestoneLabel,
      LocalDate nextMilestoneDate
  ) {
    this.batchId = batchId;
    this.batchNumber = batchNumber;
    this.incubatorName = incubatorName;
    this.breedSummary = breedSummary;
    this.numEggsSet = numEggsSet;
    this.expectedHatchDate = expectedHatchDate;
    this.nextMilestoneLabel = nextMilestoneLabel;
    this.nextMilestoneDate = nextMilestoneDate;
  }

  public long getBatchId() {
    return batchId;
  }

  public Integer getBatchNumber() {
    return batchNumber;
  }

  public String getIncubatorName() {
    return incubatorName;
  }

  public String getBreedSummary() {
    return breedSummary;
  }

  public int getNumEggsSet() {
    return numEggsSet;
  }

  public LocalDate getExpectedHatchDate() {
    return expectedHatchDate;
  }

  public String getNextMilestoneLabel() {
    return nextMilestoneLabel;
  }

  public LocalDate getNextMilestoneDate() {
    return nextMilestoneDate;
  }
}