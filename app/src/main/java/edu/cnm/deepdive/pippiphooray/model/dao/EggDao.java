package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.pojo.EggWithEggGroup;
import java.util.List;

@Dao
public interface EggDao {

  @Insert
  long insert(Egg egg);

  @Update
  void update(Egg egg);

  @Query("SELECT * FROM egg WHERE egg_group_id = :eggGroupId")
  LiveData<List<Egg>> selectByEggGroup(long eggGroupId);

  @Query("SELECT * FROM egg WHERE egg_id = :eggId")
  LiveData<Egg> select(long eggId);

  @Query("SELECT * FROM egg_with_egg_group WHERE breed = :breed")
  LiveData<EggWithEggGroup> selectByBreed(String breed);


}
