package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchEggAggregate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Abstraction for accessing and modifying {@link Egg} entities and related aggregate data.
 */
public interface EggRepository {

  /**
   * Returns all eggs belonging to a given egg group.
   *
   * @param eggGroupId egg-group ID.
   * @return live list of eggs for the specified egg group.
   */
  LiveData<List<Egg>> getByEggGroupId(long eggGroupId);

  /**
   * Returns a single egg by ID.
   *
   * @param id egg ID.
   * @return live egg with the specified ID.
   */
  LiveData<Egg> get(long id);

  /**
   * Returns aggregate viability data grouped by batch.
   *
   * @return live list of batch egg aggregates.
   */
  LiveData<List<BatchEggAggregate>> getViabilityPerBatch();

  /**
   * Fetches all eggs for a given egg group asynchronously.
   *
   * @param eggGroupId egg-group ID.
   * @return future completing with the eggs in the specified egg group.
   */
  CompletableFuture<List<Egg>> fetchByEggGroupId(long eggGroupId);

  /**
   * Inserts or updates an egg.
   *
   * @param egg egg to save.
   * @return future completing with the saved egg ID.
   */
  CompletableFuture<Long> save(Egg egg);

  /**
   * Inserts or updates multiple eggs.
   *
   * @param eggs eggs to save.
   * @return future completing when all eggs have been saved.
   */
  CompletableFuture<Void> saveAll(List<Egg> eggs);

  /**
   * Creates the initial egg records for a given egg group.
   *
   * @param eggGroupId egg-group ID.
   * @param initialEggCount number of eggs to create in the group.
   * @return future completing when the egg records have been created.
   */
  CompletableFuture<Void> seedEggsForGroup(long eggGroupId, int initialEggCount);

}