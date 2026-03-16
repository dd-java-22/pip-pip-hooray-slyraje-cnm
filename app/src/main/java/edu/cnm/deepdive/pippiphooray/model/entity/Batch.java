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
            childColumns = "incubator_id"
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

  @ColumnInfo(name = "incubator_id")
  private long incubatorId;

  @NonNull
  private String breed;

  @ColumnInfo(name = "date_set")
  private LocalDate dateSet;

  @ColumnInfo(name = "lockdown_date")
  private LocalDate lockdownDate;

  @ColumnInfo(name = "expected_hatch_date")
  private LocalDate expectedHatchDate;

  @ColumnInfo(name = "num_eggs_set")
  private int numEggsSet;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getIncubatorId() {
    return incubatorId;
  }

  public void setIncubatorId(long incubatorId) {
    this.incubatorId = incubatorId;
  }

  @NonNull
  public String getBreed() {
    return breed;
  }

  public void setBreed(@NonNull String breed) {
    this.breed = breed;
  }

  public LocalDate getDateSet() {
    return dateSet;
  }

  public void setDateSet(LocalDate dateSet) {
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
}
