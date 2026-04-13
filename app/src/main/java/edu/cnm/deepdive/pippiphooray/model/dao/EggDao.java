package edu.cnm.deepdive.pippiphooray.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchEggAggregate;
import java.util.List;

/**
 * Room Data Access Object (DAO) for Egg entity operations.
 */
@Dao
public interface EggDao {

  /**
   * Inserts a single egg into the database.
   *
   * @param egg the egg to insert
   * @return the generated egg ID
   */
  @Insert
  long insert(Egg egg);

  /**
   * Inserts multiple eggs into the database.
   *
   * @param eggs the list of eggs to insert
   * @return a list of generated egg IDs
   */
  @Insert
  List<Long> insertAll(List<Egg> eggs);

  /**
   * Updates an existing egg in the database.
   *
   * @param egg the egg to update
   */
  @Update
  void update(Egg egg);

  /**
   * Updates multiple existing eggs in the database.
   *
   * @param eggs the list of eggs to update
   */
  @Update
  void update(List<Egg> eggs);

  /**
   * Retrieves all eggs for a specific egg group as LiveData.
   *
   * @param eggGroupId the ID of the egg group
   * @return a LiveData list of eggs in the specified group
   */
  @Query("SELECT * FROM egg WHERE egg_group_id = :eggGroupId")
  LiveData<List<Egg>> selectByEggGroup(long eggGroupId);

  /**
   * Fetches all eggs for a specific egg group (non-LiveData).
   *
   * @param eggGroupId the ID of the egg group
   * @return a list of eggs in the specified group, ordered by egg number
   */
  @Query("SELECT * FROM egg WHERE egg_group_id = :eggGroupId ORDER BY egg_number ASC")
  List<Egg> fetchByEggGroup(long eggGroupId);

  /**
   * Retrieves a specific egg by ID as LiveData.
   *
   * @param eggId the ID of the egg to retrieve
   * @return a LiveData containing the egg with the specified ID
   */
  @Query("SELECT * FROM egg WHERE egg_id = :eggId")
  LiveData<Egg> select(long eggId);

  /**
   * Retrieves viability statistics aggregated by batch.
   *
   * @return a LiveData list of BatchEggAggregate objects containing viability counts per batch
   */
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