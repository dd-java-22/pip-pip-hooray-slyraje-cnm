package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;

public class EggGroupWithBatch {

  @Embedded
  private EggGroup eggGroup;

  @Relation(
      parentColumn = "batch_id",
      entityColumn = "batch_id"
  )
  private Batch batch;

  public EggGroup getEggGroup() {
    return eggGroup;
  }

  public void setEggGroup(EggGroup eggGroup) {
    this.eggGroup = eggGroup;
  }

  public Batch getBatch() {
    return batch;
  }

  public void setBatch(Batch batch) {
    this.batch = batch;
  }
}
