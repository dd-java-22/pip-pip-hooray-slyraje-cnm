package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView(
    viewName = "egg_with_egg_group",
    value = """
    SELECT
      e.egg_id,
      e.hatch_status,
      e.final_notes,
      g.egg_group_id,
      g.breed
    FROM
      egg AS e
      INNER JOIN egg_group AS g
        ON e.egg_group_id = g.egg_group_id
    ORDER BY
      g.egg_group_id,
      e.egg_id
    
    """
)
public class EggWithEggGroup {

  @ColumnInfo(name = "egg_id")
  private long id;

  @ColumnInfo(name = "hatch_status")
  private String hatchStatus;

  @ColumnInfo(name = "final_notes")
  private String finalNotes;

  @ColumnInfo(name = "egg_group_id")
  private long eggGroupId;

  @NonNull
  private String breed = "";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public long getEggGroupId() {
    return eggGroupId;
  }

  public void setEggGroupId(long eggGroupId) {
    this.eggGroupId = eggGroupId;
  }

  @NonNull
  public String getBreed() {
    return breed;
  }

  public void setBreed(@NonNull String breed) {
    this.breed = breed;
  }
}
