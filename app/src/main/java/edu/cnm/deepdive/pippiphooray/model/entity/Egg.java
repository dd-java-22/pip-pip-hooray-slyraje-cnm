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

public class Egg {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "egg_id")
  private long id;

  @ColumnInfo(name = "egg_group_id")
  private long eggGroupId;

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

  public long getEggGroupId() {
    return eggGroupId;
  }

  public void setEggGroupId(long eggGroupId) {
    this.eggGroupId = eggGroupId;
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
