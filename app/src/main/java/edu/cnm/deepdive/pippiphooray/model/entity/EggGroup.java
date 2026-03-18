package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
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
public class EggGroup {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "egg_group_id")
  private long id;

  @ColumnInfo(name = "batch_id")
  private long batchId;

  @NonNull
  private String breed;

  @ColumnInfo(name = "initial_egg_count")
  private int initialEggCount;

  private String notes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getBatchId() {
    return batchId;
  }

  public void setBatchId(long batchId) {
    this.batchId = batchId;
  }

  @NonNull
  public String getBreed() {
    return breed;
  }

  public void setBreed(@NonNull String breed) {
    this.breed = breed;
  }

  public int getInitialEggCount() {
    return initialEggCount;
  }

  public void setInitialEggCount(int initialEggCount) {
    this.initialEggCount = initialEggCount;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
