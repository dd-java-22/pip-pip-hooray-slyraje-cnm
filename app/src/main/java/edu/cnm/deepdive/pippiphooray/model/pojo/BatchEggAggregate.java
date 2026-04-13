package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.ColumnInfo;

public class BatchEggAggregate {

  @ColumnInfo(name = "batchId")
  private long batchId;

  @ColumnInfo(name = "totalCount")
  private int totalCount;

  @ColumnInfo(name = "viableCount")
  private int viableCount;

  public long getBatchId() { return batchId; }
  public void setBatchId(long batchId) { this.batchId = batchId; }

  public int getTotalCount() { return totalCount; }
  public void setTotalCount(int totalCount) { this.totalCount = totalCount; }

  public int getViableCount() { return viableCount; }
  public void setViableCount(int viableCount) { this.viableCount = viableCount; }
}