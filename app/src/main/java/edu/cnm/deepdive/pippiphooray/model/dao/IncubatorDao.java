package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.List;

@Dao
public interface IncubatorDao {

  @Insert
  long insert(Incubator incubator);

  @Update
  long update(Incubator incubator);

  // No @Delete — use @Update to set active=0 for soft deletion.

  @Query("SELECT * FROM incubator WHERE active=1 ORDER BY name ASC")
  LiveData<List<Incubator>> selectAll();

  @Query("SELECT * FROM incubator WHERE incubator_id=:id")
  LiveData<Incubator> select(long id);
}
