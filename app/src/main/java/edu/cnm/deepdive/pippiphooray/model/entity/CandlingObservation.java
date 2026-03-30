package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "candling_observation",
    foreignKeys = {
        @ForeignKey(
            entity = Egg.class,
            parentColumns = "egg_id",
            childColumns = "egg_id",
            onDelete = ForeignKey.CASCADE
        )

    },
    indices = {
        @Index(value = {"egg_id"}),
    }
)

public class CandlingObservation {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "observation_id")
  private long id;

  @ColumnInfo(name = "egg_id")
  private long eggId;

  @ColumnInfo(name = "day_number")
  private int dayNumber;

  @ColumnInfo(name = "development_status")
  @Nullable
  private String developmentStatus;

  @ColumnInfo(name = "notes")
  @Nullable
  private String notes;


  @SuppressWarnings("NotNullFieldNotInitialized")
  @ColumnInfo(name = "timestamp")
  @NonNull
  private Instant timestamp;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getEggId() {
    return eggId;
  }

  public void setEggId(long eggId) {
    this.eggId = eggId;
  }

  public int getDayNumber() {
    return dayNumber;
  }

  public void setDayNumber(int dayNumber) {
    this.dayNumber = dayNumber;
  }

  @Nullable
  public String getDevelopmentStatus() {
    return developmentStatus;
  }

  public void setDevelopmentStatus(@Nullable String developmentStatus) {
    this.developmentStatus = developmentStatus;
  }

  @Nullable
  public String getNotes() {
    return notes;
  }

  public void setNotes(@Nullable String notes) {
    this.notes = notes;
  }

  @NonNull
  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(@NonNull Instant timestamp) {
    this.timestamp = timestamp;
  }
}
