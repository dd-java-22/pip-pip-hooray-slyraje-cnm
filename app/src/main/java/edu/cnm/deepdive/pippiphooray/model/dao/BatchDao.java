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

/**
 * Room Data Access Object (DAO) for Batch entity operations.
 */
@Dao
public interface BatchDao {

  /**
   * Inserts a single batch into the database.
   *
   * @param batch the batch to insert
   * @return the generated batch ID
   */
  @Insert
  long insert(Batch batch);

  /**
   * Updates an existing batch in the database.
   *
   * @param batch the batch to update
   */
  @Update
  void update(Batch batch);

  /**
   * Deletes a batch from the database.
   *
   * @param batch the batch to delete
   */
  @Delete
  void delete(Batch batch);

  /**
   * Retrieves all batches ordered by date set (descending) as LiveData.
   *
   * @return a LiveData list of all batches
   */
  @Query("SELECT * FROM batch ORDER BY date_set DESC")
  LiveData<List<Batch>> selectAll();

  /**
   * Retrieves a specific batch by ID as LiveData.
   *
   * @param id the ID of the batch to retrieve
   * @return a LiveData containing the batch with the specified ID
   */
  @Query("SELECT * FROM batch WHERE batch_id = :id")
  LiveData<Batch> select(long id);

  /**
   * Retrieves all batches for a specific incubator as LiveData.
   *
   * @param incubatorId the ID of the incubator
   * @return a LiveData list of batches for the specified incubator
   */
  @Query("SELECT * FROM batch WHERE incubator_id = :incubatorId ORDER by date_set DESC")
  LiveData<List<Batch>> selectByIncubator(long incubatorId);

  /**
   * Retrieves a specific batch with its associated egg groups as LiveData.
   *
   * @param id the ID of the batch to retrieve
   * @return a LiveData containing the batch with its egg groups
   */
  @Transaction
  @Query("SELECT * FROM batch WHERE batch_id = :id")
  LiveData<BatchWithEggGroups> selectWithGroups(long id);

  /**
   * Retrieves all batches with their associated egg groups as LiveData.
   *
   * @return a LiveData list of all batches with their egg groups
   */
  @Transaction
  @Query("SELECT * FROM batch ORDER BY date_set DESC")
  LiveData<List<BatchWithEggGroups>> selectAllWithGroups();

  /**
   * Retrieves all batches with incubator information as LiveData.
   *
   * @return a LiveData list of all batches with incubator details
   */
  @Query("SELECT * FROM batch_with_incubator")
  LiveData<List<BatchWithIncubator>> selectAllWithIncubator();

  /**
   * Retrieves all batches with incubator information, ordered by expected hatch date (ascending).
   *
   * @return a LiveData list of all batches with incubator details, sorted by hatch date
   */
  @Query("SELECT * FROM batch_with_incubator ORDER BY expected_hatch_date ASC")
  LiveData<List<BatchWithIncubator>> selectAllWithIncubatorOrderByExpectedHatch();

  /**
   * Retrieves all batches with incubator information, ordered by incubator name then date set.
   *
   * @return a LiveData list of all batches with incubator details, sorted by name then date
   */
  @Query("SELECT * FROM batch_with_incubator ORDER BY incubator_name ASC, date_set DESC")
  LiveData<List<BatchWithIncubator>> selectAllWithIncubatorOrderByIncubator();

  /**
   * Retrieves all batches with incubator information, ordered by batch number then date set.
   *
   * @return a LiveData list of all batches with incubator details, sorted by batch number
   */
  @Query("SELECT * FROM batch_with_incubator ORDER BY batch_number IS NULL, batch_number ASC, date_set DESC")
  LiveData<List<BatchWithIncubator>> selectAllWithIncubatorOrderByBatchNumber();

}