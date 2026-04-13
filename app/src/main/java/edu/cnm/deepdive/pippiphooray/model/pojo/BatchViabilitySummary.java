package edu.cnm.deepdive.pippiphooray.model.pojo;

public class BatchViabilitySummary {

  private final int viableCount;
  private final int totalCount;

  public BatchViabilitySummary(int viableCount, int totalCount) {
    this.viableCount = viableCount;
    this.totalCount = totalCount;
  }

  public int getViableCount() {
    return viableCount;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public double getViableRate() {
    return (totalCount > 0) ? ((double) viableCount / totalCount) : 0.0;
  }

}