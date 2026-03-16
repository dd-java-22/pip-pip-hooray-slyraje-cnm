package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;

public class EggGroupWithBatch extends EggGroup {

  @Relation(
      parentColumn = "batch_id",
      entityColumn = "batch_id"
  )
private Batch batch;

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }
}
