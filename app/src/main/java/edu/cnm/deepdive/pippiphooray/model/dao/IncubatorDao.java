package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.List;

/**
 * Room Data Access Object (DAO) for Incubator entity operations.
 */
@Dao
public interface IncubatorDao {

  /**
   * Inserts a single incubator into the database.
   *
   * @param incubator the incubator to insert
   * @return the generated incubator ID
   */
  @Insert
  long insert(Incubator incubator);

  /**
   * Updates an existing incubator in the database.
   *
   * @param incubator the incubator to update
   */
  @Update
  void update(Incubator incubator);

  // No @Delete — use @Update to set active=0 for soft deletion.

  /**
   * Retrieves all active incubators ordered by name as LiveData.
   *
   * @return a LiveData list of all active incubators
   */
  @Query("SELECT * FROM incubator WHERE active=1 ORDER BY name ASC")
  LiveData<List<Incubator>> selectAll();

  /**
   * Retrieves a specific incubator by ID as LiveData.
   *
   * @param id the ID of the incubator to retrieve
   * @return a LiveData containing the incubator with the specified ID
   */
  @Query("SELECT * FROM incubator WHERE incubator_id=:id")
  LiveData<Incubator> select(long id);
}