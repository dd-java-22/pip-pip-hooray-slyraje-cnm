package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;

/**
 * Represents a candling observation recorded for a specific egg.
 *
 * <p>Each observation stores the day of incubation, a development status,
 * optional notes, and the time the observation was recorded.
 */
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

  /**
   * Returns the unique ID of this observation.
   *
   * @return observation ID.
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique ID of this observation.
   *
   * @param id observation ID.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the ID of the egg associated with this observation.
   *
   * @return related egg ID.
   */
  public long getEggId() {
    return eggId;
  }

  /**
   * Sets the ID of the egg associated with this observation.
   *
   * @param eggId related egg ID.
   */
  public void setEggId(long eggId) {
    this.eggId = eggId;
  }

  /**
   * Returns the day number of incubation for this observation.
   *
   * @return incubation day number.
   */
  public int getDayNumber() {
    return dayNumber;
  }

  /**
   * Sets the day number of incubation for this observation.
   *
   * @param dayNumber incubation day number.
   */
  public void setDayNumber(int dayNumber) {
    this.dayNumber = dayNumber;
  }

  /**
   * Returns the recorded development status for the egg.
   *
   * @return development status, or {@code null} if not specified.
   */
  @Nullable
  public String getDevelopmentStatus() {
    return developmentStatus;
  }

  /**
   * Sets the recorded development status for the egg.
   *
   * @param developmentStatus development status, or {@code null}.
   */
  public void setDevelopmentStatus(@Nullable String developmentStatus) {
    this.developmentStatus = developmentStatus;
  }

  /**
   * Returns any notes recorded for this observation.
   *
   * @return observation notes, or {@code null} if none were recorded.
   */
  @Nullable
  public String getNotes() {
    return notes;
  }

  /**
   * Sets the notes recorded for this observation.
   *
   * @param notes observation notes, or {@code null}.
   */
  public void setNotes(@Nullable String notes) {
    this.notes = notes;
  }

  /**
   * Returns the timestamp when this observation was recorded.
   *
   * @return observation timestamp.
   */
  @NonNull
  public Instant getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the timestamp when this observation was recorded.
   *
   * @param timestamp observation timestamp.
   */
  public void setTimestamp(@NonNull Instant timestamp) {
    this.timestamp = timestamp;
  }
}