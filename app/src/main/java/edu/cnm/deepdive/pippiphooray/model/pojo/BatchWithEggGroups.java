package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;

/**
 * Room relation view that combines a {@link Batch} with its {@link EggGroup} records.
 */
public class BatchWithEggGroups {

  @Embedded
  private Batch batch;

  @Relation(
      parentColumn = "batch_id",
      entityColumn = "batch_id",
      entity = EggGroup.class
  )
  private List<EggGroup> groups;

  /**
   * Returns the batch in this view.
   *
   * @return batch entity.
   */
  public Batch getBatch() {
    return batch;
  }

  /**
   * Sets the batch in this view.
   *
   * @param batch batch entity.
   */
  public void setBatch(Batch batch) {
    this.batch = batch;
  }

  /**
   * Returns the egg groups associated with the batch.
   *
   * @return list of egg groups, or {@code null} if not loaded.
   */
  public List<EggGroup> getGroups() {
    return groups;
  }

  /**
   * Sets the egg groups associated with the batch.
   *
   * @param groups list of egg groups.
   */
  public void setGroups(List<EggGroup> groups) {
    this.groups = groups;
  }
}