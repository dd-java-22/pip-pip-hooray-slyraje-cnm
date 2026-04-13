package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.ColumnInfo;

/**
 * Projection that aggregates egg counts for a single batch.
 *
 * <p>Each instance contains the batch ID, the total number of eggs,
 * and the number of eggs currently marked as viable.
 */
public class BatchEggAggregate {

  @ColumnInfo(name = "batchId")
  private long batchId;

  @ColumnInfo(name = "totalCount")
  private int totalCount;

  @ColumnInfo(name = "viableCount")
  private int viableCount;

  /**
   * Returns the ID of the batch for this aggregate.
   *
   * @return batch ID.
   */
  public long getBatchId() { return batchId; }

  /**
   * Sets the ID of the batch for this aggregate.
   *
   * @param batchId batch ID.
   */
  public void setBatchId(long batchId) { this.batchId = batchId; }

  /**
   * Returns the total number of eggs in the batch.
   *
   * @return total egg count.
   */
  public int getTotalCount() { return totalCount; }

  /**
   * Sets the total number of eggs in the batch.
   *
   * @param totalCount total egg count.
   */
  public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

  /**
   * Returns the number of viable eggs in the batch.
   *
   * @return viable egg count.
   */
  public int getViableCount() { return viableCount; }

  /**
   * Sets the number of viable eggs in the batch.
   *
   * @param viableCount viable egg count.
   */
  public void setViableCount(int viableCount) { this.viableCount = viableCount; }
}