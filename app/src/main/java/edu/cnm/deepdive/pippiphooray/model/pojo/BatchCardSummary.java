package edu.cnm.deepdive.pippiphooray.model.pojo;

import java.time.LocalDate;

/**
 * Data transfer object containing summary information for a batch card display.
 */
public class BatchCardSummary {

  private final long batchId;
  private final Integer batchNumber;
  private final String incubatorName;
  private final String breedSummary;
  private final int viableCount;
  private final int totalEggCount;
  private final double viabilityRate;
  private final LocalDate expectedHatchDate;
  private final String nextMilestoneLabel;
  private final LocalDate nextMilestoneDate;

  /**
   * Constructs a BatchCardSummary with all required information.
   *
   * @param batchId the unique identifier for the batch
   * @param batchNumber the batch reference number
   * @param incubatorName the name of the incubator for this batch
   * @param breedSummary a summary of the breeds in this batch
   * @param viableCount the count of viable eggs
   * @param totalEggCount the total count of eggs in the batch
   * @param viabilityRate the calculated viability rate (0.0 to 1.0)
   * @param expectedHatchDate the expected hatch date
   * @param nextMilestoneLabel the label for the next milestone
   * @param nextMilestoneDate the date of the next milestone
   */
  public BatchCardSummary(
      long batchId,
      Integer batchNumber,
      String incubatorName,
      String breedSummary,
      int viableCount,
      int totalEggCount,
      double viabilityRate,
      LocalDate expectedHatchDate,
      String nextMilestoneLabel,
      LocalDate nextMilestoneDate
  ) {
    this.batchId = batchId;
    this.batchNumber = batchNumber;
    this.incubatorName = incubatorName;
    this.breedSummary = breedSummary;
    this.viableCount = viableCount;
    this.totalEggCount = totalEggCount;
    this.viabilityRate = viabilityRate;
    this.expectedHatchDate = expectedHatchDate;
    this.nextMilestoneLabel = nextMilestoneLabel;
    this.nextMilestoneDate = nextMilestoneDate;
  }

  /**
   * Gets the unique identifier for the batch.
   *
   * @return the batch ID
   */
  public long getBatchId() {
    return batchId;
  }

  /**
   * Gets the batch reference number.
   *
   * @return the batch number, or null if not assigned
   */
  public Integer getBatchNumber() {
    return batchNumber;
  }

  /**
   * Gets the name of the incubator for this batch.
   *
   * @return the incubator name
   */
  public String getIncubatorName() {
    return incubatorName;
  }

  /**
   * Gets the breed summary for this batch.
   *
   * @return the breed summary
   */
  public String getBreedSummary() {
    return breedSummary;
  }

  /**
   * Gets the count of viable eggs in this batch.
   *
   * @return the viable egg count
   */
  public int getViableCount() {
    return viableCount;
  }

  /**
   * Gets the total count of eggs in this batch.
   *
   * @return the total egg count
   */
  public int getTotalEggCount() {
    return totalEggCount;
  }

  /**
   * Gets the viability rate of this batch.
   *
   * @return the viability rate as a decimal (0.0 to 1.0)
   */
  public double getViabilityRate() {
    return viabilityRate;
  }

  /**
   * Gets the expected hatch date for this batch.
   *
   * @return the expected hatch date
   */
  public LocalDate getExpectedHatchDate() {
    return expectedHatchDate;
  }

  /**
   * Gets the label for the next milestone event.
   *
   * @return the next milestone label
   */
  public String getNextMilestoneLabel() {
    return nextMilestoneLabel;
  }

  /**
   * Gets the date of the next milestone event.
   *
   * @return the next milestone date
   */
  public LocalDate getNextMilestoneDate() {
    return nextMilestoneDate;
  }
}