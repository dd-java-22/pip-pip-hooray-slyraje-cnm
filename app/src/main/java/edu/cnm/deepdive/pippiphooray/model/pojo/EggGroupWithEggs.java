package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;


/**
 * Room relation object combining an egg group with its associated eggs.
 */
public class EggGroupWithEggs {

  @Embedded
  private EggGroup eggGroup;

  @Relation(
      parentColumn = "egg_group_id",
      entityColumn = "egg_group_id"
  )

  private List<Egg> eggs;

  /**
   * Gets the egg group.
   *
   * @return the egg group
   */
  public EggGroup getEggGroup() {
    return eggGroup;
  }

  /**
   * Sets the egg group.
   *
   * @param eggGroup the egg group to set
   */
  public void setEggGroup(EggGroup eggGroup) {
    this.eggGroup = eggGroup;
  }

  /**
   * Gets the list of eggs in this group.
   *
   * @return the list of eggs
   */
  public List<Egg> getEggs() {
    return eggs;
  }

  /**
   * Sets the list of eggs in this group.
   *
   * @param eggs the list of eggs to set
   */
  public void setEggs(List<Egg> eggs) {
    this.eggs = eggs;
  }
}