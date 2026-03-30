package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.util.List;

@Dao
public interface CandlingObservationDao {

  @Insert
  long insert(CandlingObservation observation);

  @Query("SELECT * FROM candling_observation WHERE egg_id = :eggId ORDER BY day_number ASC")
  LiveData<List<CandlingObservation>> selectByEgg(long eggId);

  @Query("SELECT * FROM candling_observation WHERE observation_id = :id")
  LiveData<CandlingObservation> select(long id);

}
