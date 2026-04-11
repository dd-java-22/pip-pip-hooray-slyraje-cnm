package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import java.util.List;

@Dao
public interface EggDao {

  @Insert
  long insert(Egg egg);

  @Insert
  List<Long> insertAll(List<Egg> eggs);

  @Update
  void update(Egg egg);

  @Update
  void update(List<Egg> eggs);

  @Query("SELECT * FROM egg WHERE egg_group_id = :eggGroupId")
  LiveData<List<Egg>> selectByEggGroup(long eggGroupId);

  @Query("SELECT * FROM egg WHERE egg_group_id = :eggGroupId ORDER BY egg_number ASC")
  List<Egg> fetchByEggGroup(long eggGroupId);

  @Query("SELECT * FROM egg WHERE egg_id = :eggId")
  LiveData<Egg> select(long eggId);
}