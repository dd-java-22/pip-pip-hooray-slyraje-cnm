package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;

/**
 * Room Data Access Object (DAO) for EggGroup entity operations.
 */
@Dao
public interface EggGroupDao {

  /**
   * Inserts a single egg group into the database.
   *
   * @param eggGroup the egg group to insert
   * @return the generated egg group ID
   */
  @Insert
  long insert(EggGroup eggGroup);

  /**
   * Inserts multiple egg groups into the database.
   *
   * @param eggGroups the list of egg groups to insert
   * @return a list of generated egg group IDs
   */
  @Insert
  List<Long> insertAll(List<EggGroup> eggGroups);

  /**
   * Updates an existing egg group in the database.
   *
   * @param eggGroup the egg group to update
   */
  @Update
  void update(EggGroup eggGroup);

  /**
   * Retrieves all egg groups for a specific batch as LiveData.
   *
   * @param batchId the ID of the batch
   * @return a LiveData list of egg groups in the specified batch
   */
  @Query("SELECT * FROM egg_group WHERE batch_id = :batchId")
  LiveData<List<EggGroup>> selectByBatch(long batchId);

  /**
   * Retrieves a specific egg group by ID as LiveData.
   *
   * @param id the ID of the egg group to retrieve
   * @return a LiveData containing the egg group with the specified ID
   */
  @Query("SELECT * FROM egg_group WHERE egg_group_id = :id")
  LiveData<EggGroup> select(long id);
}