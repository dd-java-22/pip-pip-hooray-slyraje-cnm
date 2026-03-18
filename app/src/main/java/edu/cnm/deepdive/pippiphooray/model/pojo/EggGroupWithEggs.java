package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;


public class EggGroupWithEggs {

  @Embedded
  private EggGroup eggGroup;

  @Relation(
      parentColumn = "egg_group_id",
      entityColumn = "egg_group_id"
  )

  private List<Egg> eggs;

  public EggGroup getEggGroup() {
    return eggGroup;
  }

  public void setEggGroup(EggGroup eggGroup) {
    this.eggGroup = eggGroup;
  }

  public List<Egg> getEggs() {
    return eggs;
  }

  public void setEggs(List<Egg> eggs) {
    this.eggs = eggs;
  }
}
