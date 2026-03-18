package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;

public class BatchWithEggGroups {

  @Embedded
  private Batch batch;

  @Relation(
      parentColumn = "batch_id",
      entityColumn = "batch_id",
      entity = EggGroup.class
  )
  private List<EggGroup> groups;

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  public List<EggGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<EggGroup> groups) {
    this.groups = groups;
  }
}
