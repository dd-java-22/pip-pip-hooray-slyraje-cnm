package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import java.util.List;

@Dao
public interface EggDao {

  @Insert
  long insert(Egg egg);

  @Update
  void update(Egg egg);

  @Query("SELECT * FROM egg WHERE batch_id = :batch_id")
  LiveData<List<Egg>> selectByBatch(long batch_id);

  @Query("SELECT * FROM egg ORDER BY egg_id")
  LiveData<Egg> select(long egg_id);


}
