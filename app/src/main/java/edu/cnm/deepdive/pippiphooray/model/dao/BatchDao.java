package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.util.List;

@Dao
public interface BatchDao {

  @Insert
  long insert(Batch batch);

  @Update
  void update(Batch batch);

  @Delete
  void delete(Batch batch);

  @Query("SELECT * FROM batch ORDER BY date_set DESC")
  LiveData<List<Batch>> selectAll();

  @Query("SELECT * FROM batch WHERE batch_id = :id")
  LiveData<Batch> select(long id);

  @Query("SELECT * FROM batch WHERE incubator_id = :incubatorId ORDER by date_set DESC")
  LiveData<List<Batch>> selectByIncubator(long incubatorId);

  @Transaction
  @Query("SELECT * FROM batch WHERE batch_id = :id")
  LiveData<BatchWithEggGroups> selectWithGroups(long id);

  @Query("SELECT * FROM batch_with_incubator")
  LiveData<List<BatchWithIncubator>> selectAllWithIncubator();
}
