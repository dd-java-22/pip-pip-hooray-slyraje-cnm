package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "egg",
    foreignKeys = {
        @ForeignKey(
            entity = EggGroup.class,
            parentColumns = "egg_group_id",
            childColumns = "egg_group_id",
            onDelete = ForeignKey.CASCADE
        )

    },
    indices = {
        @Index(value = {"egg_group_id"}),
    }
)

/**
 * Room entity representing a single egg in an incubation batch.
 */
public class Egg {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "egg_id")
  private long id;

  @ColumnInfo(name = "egg_group_id")
  private long eggGroupId;

  @ColumnInfo(name = "egg_number")
  private int eggNumber;

  @ColumnInfo(name = "hatch_status")
  @Nullable
  private String hatchStatus;

  @ColumnInfo(name = "final_notes")
  @Nullable
  private String finalNotes;

  /**
   * Gets the unique identifier for this egg.
   *
   * @return the egg ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique identifier for this egg.
   *
   * @param id the egg ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the ID of the egg group this egg belongs to.
   *
   * @return the egg group ID
   */
  public long getEggGroupId() {
    return eggGroupId;
  }

  /**
   * Sets the ID of the egg group this egg belongs to.
   *
   * @param eggGroupId the egg group ID to set
   */
  public void setEggGroupId(long eggGroupId) {
    this.eggGroupId = eggGroupId;
  }

  /**
   * Gets the sequence number of this egg within its group.
   *
   * @return the egg number
   */
  public int getEggNumber() {
    return eggNumber;
  }

  /**
   * Sets the sequence number of this egg within its group.
   *
   * @param eggNumber the egg number to set
   */
  public void setEggNumber(int eggNumber) {
    this.eggNumber = eggNumber;
  }

  /**
   * Gets the hatch status of this egg.
   *
   * @return the hatch status, or null if not yet determined
   */
  @Nullable
  public String getHatchStatus() {
    return hatchStatus;
  }

  /**
   * Sets the hatch status of this egg.
   *
   * @param hatchStatus the hatch status to set
   */
  public void setHatchStatus(@Nullable String hatchStatus) {
    this.hatchStatus = hatchStatus;
  }

  /**
   * Gets any final notes associated with this egg.
   *
   * @return the final notes, or null if none recorded
   */
  @Nullable
  public String getFinalNotes() {
    return finalNotes;
  }

  /**
   * Sets final notes associated with this egg.
   *
   * @param finalNotes the final notes to set
   */
  public void setFinalNotes(@Nullable String finalNotes) {
    this.finalNotes = finalNotes;
  }

}