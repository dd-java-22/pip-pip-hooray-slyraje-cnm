package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import java.util.List;

public class BatchWithEggGroups {

  @Embedded
  private Batch batch;
  @Relation(
      parentColumn = "batch_id",
      entityColumn = "batch_id"
  )
  private List<EggGroupWithEggs> groups;

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  public List<EggGroupWithEggs> getGroups() {
    return groups;
  }

  public void setGroups(List<EggGroupWithEggs> groups) {
    this.groups = groups;
  }
}
