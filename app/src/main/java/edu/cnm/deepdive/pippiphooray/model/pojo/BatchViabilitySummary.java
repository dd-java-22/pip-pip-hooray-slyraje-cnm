package edu.cnm.deepdive.pippiphooray.model.pojo;

/**
 * Data transfer object containing viability statistics for a batch.
 */
public class BatchViabilitySummary {

  private final int viableCount;
  private final int totalCount;

  /**
   * Constructs a BatchViabilitySummary with viability counts.
   *
   * @param viableCount the count of viable eggs
   * @param totalCount the total count of eggs
   */
  public BatchViabilitySummary(int viableCount, int totalCount) {
    this.viableCount = viableCount;
    this.totalCount = totalCount;
  }

  /**
   * Gets the count of viable eggs.
   *
   * @return the viable egg count
   */
  public int getViableCount() {
    return viableCount;
  }

  /**
   * Gets the total count of eggs.
   *
   * @return the total egg count
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * Gets the calculated viability rate.
   *
   * @return the viability rate as a decimal (0.0 to 1.0), or 0.0 if total is zero
   */
  public double getViableRate() {
    return (totalCount > 0) ? ((double) viableCount / totalCount) : 0.0;
  }

}