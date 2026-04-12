package edu.cnm.deepdive.pippiphooray.model.pojo;

import java.time.LocalDate;

public class BatchCardSummary {

  private final long batchId;
  private final Integer batchNumber;
  private final String incubatorName;
  private final String breedSummary;
  private final int numEggsSet;
  private final LocalDate expectedHatchDate;

  public BatchCardSummary(
      long batchId,
      Integer batchNumber,
      String incubatorName,
      String breedSummary,
      int numEggsSet,
      LocalDate expectedHatchDate
  ) {
    this.batchId = batchId;
    this.batchNumber = batchNumber;
    this.incubatorName = incubatorName;
    this.breedSummary = breedSummary;
    this.numEggsSet = numEggsSet;
    this.expectedHatchDate = expectedHatchDate;
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
}