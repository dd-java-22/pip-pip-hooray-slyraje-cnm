package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchEggAggregate;
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

  @Query(
      "SELECT e.egg_group_id AS eggGroupId, " +
          "       eg.batch_id    AS batchId, " +
          "       COUNT(*)       AS totalCount, " +
          "       SUM(CASE WHEN e.hatch_status = 'VIABLE' THEN 1 ELSE 0 END) AS viableCount " +
          "FROM egg e " +
          "JOIN egg_group eg ON e.egg_group_id = eg.egg_group_id " +
          "GROUP BY eg.batch_id"
  )
  LiveData<List<BatchEggAggregate>> selectViabilityPerBatch();
}