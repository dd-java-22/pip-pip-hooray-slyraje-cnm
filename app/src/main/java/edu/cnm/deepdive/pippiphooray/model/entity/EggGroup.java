package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "egg_group",
    foreignKeys = {
        @ForeignKey(
            entity = Batch.class,
            parentColumns = "batch_id",
            childColumns = "batch_id",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("batch_id")
    }
)

/**
 * Room entity representing a group of eggs of the same breed within a batch.
 */
public class EggGroup {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "egg_group_id")
  private long id;

  @ColumnInfo(name = "batch_id")
  private long batchId;

  @SuppressWarnings("NotNullFieldNotInitialized")
  @NonNull
  private String breed;

  @ColumnInfo(name = "initial_egg_count")
  private int initialEggCount;

  @Nullable
  private String notes;

  /**
   * Gets the unique identifier for this egg group.
   *
   * @return the egg group ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique identifier for this egg group.
   *
   * @param id the egg group ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the ID of the batch this egg group belongs to.
   *
   * @return the batch ID
   */
  public long getBatchId() {
    return batchId;
  }

  /**
   * Sets the ID of the batch this egg group belongs to.
   *
   * @param batchId the batch ID to set
   */
  public void setBatchId(long batchId) {
    this.batchId = batchId;
  }

  /**
   * Gets the breed of eggs in this group.
   *
   * @return the breed name
   */
  @NonNull
  public String getBreed() {
    return breed;
  }

  /**
   * Sets the breed of eggs in this group.
   *
   * @param breed the breed name to set
   */
  public void setBreed(@NonNull String breed) {
    this.breed = breed;
  }

  /**
   * Gets the initial count of eggs in this group.
   *
   * @return the initial egg count
   */
  public int getInitialEggCount() {
    return initialEggCount;
  }

  /**
   * Sets the initial count of eggs in this group.
   *
   * @param initialEggCount the initial egg count to set
   */
  public void setInitialEggCount(int initialEggCount) {
    this.initialEggCount = initialEggCount;
  }

  /**
   * Gets any notes associated with this egg group.
   *
   * @return the notes, or null if none recorded
   */
  @Nullable
  public String getNotes() {
    return notes;
  }

  /**
   * Sets notes associated with this egg group.
   *
   * @param notes the notes to set
   */
  public void setNotes(@Nullable String notes) {
    this.notes = notes;
  }
}