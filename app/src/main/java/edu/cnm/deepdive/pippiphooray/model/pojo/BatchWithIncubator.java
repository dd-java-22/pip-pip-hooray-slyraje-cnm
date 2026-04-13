package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import java.time.LocalDate;

/**
 * Database view that combines batch data with its associated incubator details.
 *
 * <p>This view exposes batch fields along with the incubator name and model
 * for use in list and summary presentations.
 */
@DatabaseView(
    viewName = "batch_with_incubator",
    value = """
        SELECT
          b.batch_id,
          b.incubator_id,
          b.batch_number,
          b.date_set,
          b.lockdown_date,
          b.expected_hatch_date,
          b.num_eggs_set,
          b.batch_status,
          i.name AS incubator_name,
          i.model AS incubator_model
        FROM batch AS b
        LEFT JOIN incubator AS i
          ON b.incubator_id = i.incubator_id
        ORDER BY b.date_set DESC
        """
)
public class BatchWithIncubator {

  @ColumnInfo(name = "batch_id")
  private long id;

  @ColumnInfo(name = "incubator_id")
  private Long incubatorId;

  @ColumnInfo(name = "batch_number")
  private Integer batchNumber;

  @ColumnInfo(name = "date_set")
  private LocalDate dateSet;

  @ColumnInfo(name = "expected_hatch_date")
  private LocalDate expectedHatchDate;

  @ColumnInfo(name = "lockdown_date")
  private LocalDate lockdownDate;

  @ColumnInfo(name = "num_eggs_set")
  private int numEggsSet;

  @ColumnInfo(name = "batch_status")
  private String batchStatus;

  @NonNull
  @ColumnInfo(name = "incubator_name")
  private String incubatorName;

  @NonNull
  @ColumnInfo(name = "incubator_model")
  private String incubatorModel;

  /**
   * Returns the batch ID.
   *
   * @return batch ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the batch ID.
   *
   * @param id batch ID.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the incubator ID associated with the batch.
   *
   * @return incubator ID, or {@code null} if none is set.
   */
  public Long getIncubatorId() {
    return incubatorId;
  }

  /**
   * Sets the incubator ID associated with the batch.
   *
   * @param incubatorId incubator ID, or {@code null}.
   */
  public void setIncubatorId(Long incubatorId) {
    this.incubatorId = incubatorId;
  }

  /**
   * Returns the batch number.
   *
   * @return batch number, or {@code null} if not assigned.
   */
  public Integer getBatchNumber() {
    return batchNumber;
  }

  /**
   * Sets the batch number.
   *
   * @param batchNumber batch number.
   */
  public void setBatchNumber(Integer batchNumber) {
    this.batchNumber = batchNumber;
  }

  /**
   * Returns the date the batch was set.
   *
   * @return date set, or {@code null} if unknown.
   */
  public LocalDate getDateSet() {
    return dateSet;
  }

  /**
   * Sets the date the batch was set.
   *
   * @param dateSet date set.
   */
  public void setDateSet(LocalDate dateSet) {
    this.dateSet = dateSet;
  }

  /**
   * Returns the lockdown date for the batch.
   *
   * @return lockdown date, or {@code null} if not specified.
   */
  public LocalDate getLockdownDate() {
    return lockdownDate;
  }

  /**
   * Sets the lockdown date for the batch.
   *
   * @param lockdownDate lockdown date.
   */
  public void setLockdownDate(LocalDate lockdownDate) {
    this.lockdownDate = lockdownDate;
  }

  /**
   * Returns the expected hatch date for the batch.
   *
   * @return expected hatch date, or {@code null} if not specified.
   */
  public LocalDate getExpectedHatchDate() {
    return expectedHatchDate;
  }

  /**
   * Sets the expected hatch date for the batch.
   *
   * @param expectedHatchDate expected hatch date.
   */
  public void setExpectedHatchDate(LocalDate expectedHatchDate) {
    this.expectedHatchDate = expectedHatchDate;
  }

  /**
   * Returns the number of eggs set in the batch.
   *
   * @return number of eggs set.
   */
  public int getNumEggsSet() {
    return numEggsSet;
  }

  /**
   * Sets the number of eggs set in the batch.
   *
   * @param numEggsSet number of eggs set.
   */
  public void setNumEggsSet(int numEggsSet) {
    this.numEggsSet = numEggsSet;
  }

  /**
   * Returns the batch status.
   *
   * @return batch status.
   */
  public String getBatchStatus() {
    return batchStatus;
  }

  /**
   * Sets the batch status.
   *
   * @param batchStatus batch status.
   */
  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
  }

  /**
   * Returns the name of the incubator.
   *
   * @return incubator name.
   */
  @NonNull
  public String getIncubatorName() {
    return incubatorName;
  }

  /**
   * Sets the name of the incubator.
   *
   * @param incubatorName incubator name.
   */
  public void setIncubatorName(@NonNull String incubatorName) {
    this.incubatorName = incubatorName;
  }

  /**
   * Returns the model of the incubator.
   *
   * @return incubator model.
   */
  @NonNull
  public String getIncubatorModel() {
    return incubatorModel;
  }

  /**
   * Sets the model of the incubator.
   *
   * @param incubatorModel incubator model.
   */
  public void setIncubatorModel(@NonNull String incubatorModel) {
    this.incubatorModel = incubatorModel;
  }
}