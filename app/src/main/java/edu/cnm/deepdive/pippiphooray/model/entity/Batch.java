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

/**
 * Room entity representing an incubation batch containing one or more egg groups.
 */
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


  /**
   * Gets the unique identifier for this batch.
   *
   * @return the batch ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique identifier for this batch.
   *
   * @param id the batch ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the ID of the incubator used for this batch.
   *
   * @return the incubator ID, or null if not assigned
   */
  public Long getIncubatorId() {
    return incubatorId;
  }

  /**
   * Sets the ID of the incubator used for this batch.
   *
   * @param incubatorId the incubator ID to set
   */
  public void setIncubatorId(Long incubatorId) {
    this.incubatorId = incubatorId;
  }

  /**
   * Gets the batch number for reference purposes.
   *
   * @return the batch number, or null if not assigned
   */
  public Integer getBatchNumber() {
    return batchNumber;
  }

  /**
   * Sets the batch number for reference purposes.
   *
   * @param batchNumber the batch number to set
   */
  public void setBatchNumber(Integer batchNumber) {
    this.batchNumber = batchNumber;
  }

  /**
   * Gets the date when eggs were set in the incubator.
   *
   * @return the date eggs were set
   */
  @NonNull
  public LocalDate getDateSet() {
    return dateSet;
  }

  /**
   * Sets the date when eggs were set in the incubator.
   *
   * @param dateSet the date to set
   */
  public void setDateSet(@NonNull LocalDate dateSet) {
    this.dateSet = dateSet;
  }

  /**
   * Gets the lockdown date (typically day 18 of incubation).
   *
   * @return the lockdown date, or null if not set
   */
  public LocalDate getLockdownDate() {
    return lockdownDate;
  }

  /**
   * Sets the lockdown date (typically day 18 of incubation).
   *
   * @param lockdownDate the lockdown date to set
   */
  public void setLockdownDate(LocalDate lockdownDate) {
    this.lockdownDate = lockdownDate;
  }

  /**
   * Gets the expected hatch date.
   *
   * @return the expected hatch date, or null if not calculated
   */
  public LocalDate getExpectedHatchDate() {
    return expectedHatchDate;
  }

  /**
   * Sets the expected hatch date.
   *
   * @param expectedHatchDate the expected hatch date to set
   */
  public void setExpectedHatchDate(LocalDate expectedHatchDate) {
    this.expectedHatchDate = expectedHatchDate;
  }

  /**
   * Gets the total number of eggs initially set in this batch.
   *
   * @return the number of eggs set
   */
  public int getNumEggsSet() {
    return numEggsSet;
  }

  /**
   * Sets the total number of eggs initially set in this batch.
   *
   * @param numEggsSet the number of eggs to set
   */
  public void setNumEggsSet(int numEggsSet) {
    this.numEggsSet = numEggsSet;
  }

  /**
   * Gets any notes associated with this batch.
   *
   * @return the notes, or null if none recorded
   */
  public String getNotes() {
    return notes;
  }

  /**
   * Sets notes associated with this batch.
   *
   * @param notes the notes to set
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Gets the current status of this batch.
   *
   * @return the batch status, or null if not set
   */
  public String getBatchStatus() {
    return batchStatus;
  }

  /**
   * Sets the current status of this batch.
   *
   * @param batchStatus the batch status to set
   */
  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
  }
}