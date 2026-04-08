package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.LocalDate;


@Entity(
    tableName = "batch",
    foreignKeys = {
        @ForeignKey(
            entity = Incubator.class,
            parentColumns = "incubator_id",
            childColumns = "incubator_id",
            onDelete = ForeignKey.SET_NULL
        )
    },
    indices = {
        @Index(value = {"incubator_id"})
    }
)

public class Batch {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "batch_id")
  private long id;

  // TODO: 3/30/2026 Set this field to @NotNull in the future when the UI is set up to require
  //  incubator selection. The ERD reflects this intent currently.
  @ColumnInfo(name = "incubator_id")
  private Long incubatorId;

  @ColumnInfo(name = "batch_number")
  private Integer batchNumber;

  @SuppressWarnings("NotNullFieldNotInitialized")
  @ColumnInfo(name = "date_set")
  @NonNull
  private LocalDate dateSet;

  @ColumnInfo(name = "lockdown_date")
  private LocalDate lockdownDate;

  @ColumnInfo(name = "expected_hatch_date")
  private LocalDate expectedHatchDate;

  @ColumnInfo(name = "num_eggs_set")
  private int numEggsSet;

  private String notes;

  @ColumnInfo(name = "batch_status")
  private String batchStatus;  // future enum candidate, same pattern as hatchStatus on Egg


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getIncubatorId() {
    return incubatorId;
  }

  public void setIncubatorId(Long incubatorId) {
    this.incubatorId = incubatorId;
  }

  public Integer getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(Integer batchNumber) {
    this.batchNumber = batchNumber;
  }

  @NonNull
  public LocalDate getDateSet() {
    return dateSet;
  }

  public void setDateSet(@NonNull LocalDate dateSet) {
    this.dateSet = dateSet;
  }

  public LocalDate getLockdownDate() {
    return lockdownDate;
  }

  public void setLockdownDate(LocalDate lockdownDate) {
    this.lockdownDate = lockdownDate;
  }

  public LocalDate getExpectedHatchDate() {
    return expectedHatchDate;
  }

  public void setExpectedHatchDate(LocalDate expectedHatchDate) {
    this.expectedHatchDate = expectedHatchDate;
  }

  public int getNumEggsSet() {
    return numEggsSet;
  }

  public void setNumEggsSet(int numEggsSet) {
    this.numEggsSet = numEggsSet;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getBatchStatus() {
    return batchStatus;
  }

  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
  }
}


