package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Abstraction for accessing and modifying {@link EggGroup} entities.
 */
public interface EggGroupRepository {

  /**
   * Returns all egg groups belonging to a given batch.
   *
   * @param batchId batch ID.
   * @return live list of egg groups for the specified batch.
   */
  LiveData<List<EggGroup>> getByBatch(long batchId);

  /**
   * Returns a single egg group by ID.
   *
   * @param id egg-group ID.
   * @return live egg group with the specified ID.
   */
  LiveData<EggGroup> get(long id);

  /**
   * Inserts or updates an egg group.
   *
   * @param eggGroup egg group to save.
   * @return future completing with the saved egg-group ID.
   */
  CompletableFuture<Long> save(EggGroup eggGroup);

  /**
   * Inserts or updates multiple egg groups.
   *
   * @param eggGroups egg groups to save.
   * @return future completing with the saved IDs in the same order.
   */
  CompletableFuture<List<Long>> saveAll(List<EggGroup> eggGroups);

}