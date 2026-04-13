package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Abstraction for accessing and modifying {@link Incubator} entities.
 */
public interface IncubatorRepository {

  /**
   * Returns all incubators that are currently marked as active.
   *
   * @return live list of active incubators.
   */
  LiveData<List<Incubator>> getActiveIncubators();

  /**
   * Returns a single incubator by ID.
   *
   * @param id incubator ID.
   * @return live incubator with the specified ID.
   */
  LiveData<Incubator> get(long id);

  /**
   * Inserts or updates an incubator.
   *
   * @param incubator incubator to save.
   * @return future completing with the saved incubator ID.
   */
  CompletableFuture<Long> save(Incubator incubator); // insert or update

  /**
   * Marks the given incubator as inactive (soft delete).
   *
   * @param incubator incubator to deactivate.
   * @return future completing when the incubator has been deactivated.
   */
  CompletableFuture<Void> deactivate(Incubator incubator); // soft delete: active = false
}