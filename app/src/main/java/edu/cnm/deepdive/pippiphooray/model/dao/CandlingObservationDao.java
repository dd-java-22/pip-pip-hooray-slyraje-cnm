package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.util.List;

/**
 * Provides database access methods for {@link CandlingObservation} entities.
 */
@Dao
public interface CandlingObservationDao {

  /**
   * Inserts a candling observation into the database.
   *
   * @param observation candling observation to insert.
   * @return row ID of the inserted observation.
   */
  @Insert
  long insert(CandlingObservation observation);

  /**
   * Returns all candling observations recorded for a specific egg.
   *
   * @param eggId ID of the egg.
   * @return live list of observations for the specified egg, ordered by incubation day.
   */
  @Query("SELECT * FROM candling_observation WHERE egg_id = :eggId ORDER BY day_number ASC")
  LiveData<List<CandlingObservation>> selectByEgg(long eggId);

  /**
   * Returns a single candling observation by ID.
   *
   * @param id observation ID.
   * @return live observation matching the specified ID.
   */
  @Query("SELECT * FROM candling_observation WHERE observation_id = :id")
  LiveData<CandlingObservation> select(long id);

}