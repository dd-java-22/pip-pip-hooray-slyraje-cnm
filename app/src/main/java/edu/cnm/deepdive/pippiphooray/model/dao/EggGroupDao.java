package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.pojo.EggGroupWithBatch;
import java.util.List;

@Dao
public interface EggGroupDao {

  @Insert
  long insert(EggGroup eggGroup);

  @Update
  void update(EggGroup eggGroup);

  @Query("SELECT * FROM egg_group WHERE batch_id = :batchId")
  LiveData<List<EggGroup>> selectByBatch(long batchId);

  @Query("SELECT * FROM egg_group WHERE egg_group_id = :id")
  LiveData<EggGroup> select(long id);

  @Transaction
  @Query("SELECT * FROM egg_group WHERE egg_group_id = :id")
  LiveData<EggGroupWithBatch> selectWithBatch(long id);

}
