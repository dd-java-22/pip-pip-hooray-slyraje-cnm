package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "egg",
    foreignKeys = {
        @ForeignKey(
            entity = Batch.class,
            parentColumns = "batch_id",
            childColumns = "batch_id",
            onDelete = ForeignKey.CASCADE
        )

    },
    indices = {
        @Index(value = {"batch_id"}),
    }
)

public class Egg {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "egg_id")
  private long id;

  @ColumnInfo(name = "batch_id")
  private long batchId;

  @ColumnInfo(name = "hatch_status")
  private String hatchStatus;

  @ColumnInfo(name = "final_notes")
  private String finalNotes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getBatchId() {
    return batchId;
  }

  public void setBatchId(long batchId) {
    this.batchId = batchId;
  }

  public String getHatchStatus() {
    return hatchStatus;
  }

  public void setHatchStatus(String hatchStatus) {
    this.hatchStatus = hatchStatus;
  }

  public String getFinalNotes() {
    return finalNotes;
  }

  public void setFinalNotes(String finalNotes) {
    this.finalNotes = finalNotes;
  }

}
