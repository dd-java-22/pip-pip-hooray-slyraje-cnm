package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import java.time.LocalDate;

@DatabaseView(
    viewName = "batch_with_incubator",
    value = """
        SELECT
          b.batch_id,
          b.incubator_id,
          b.batch_number,
          b.date_set,
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

  public LocalDate getDateSet() {
    return dateSet;
  }

  public void setDateSet(LocalDate dateSet) {
    this.dateSet = dateSet;
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

  public String getBatchStatus() {
    return batchStatus;
  }

  public void setBatchStatus(String batchStatus) {
    this.batchStatus = batchStatus;
  }

  @NonNull
  public String getIncubatorName() {
    return incubatorName;
  }

  public void setIncubatorName(@NonNull String incubatorName) {
    this.incubatorName = incubatorName;
  }

  @NonNull
  public String getIncubatorModel() {
    return incubatorModel;
  }

  public void setIncubatorModel(@NonNull String incubatorModel) {
    this.incubatorModel = incubatorModel;
  }
}