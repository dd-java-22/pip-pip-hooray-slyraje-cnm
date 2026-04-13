package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Abstraction for accessing and modifying {@link Batch} entities and related views.
 */
public interface BatchRepository {

  /**
   * Returns all batches.
   *
   * @return live list of all batches.
   */
  LiveData<List<Batch>> getAll();

  /**
   * Returns a single batch by ID.
   *
   * @param id batch ID.
   * @return live batch with the specified ID.
   */
  LiveData<Batch> get(long id);

  /**
   * Returns all batches associated with a given incubator.
   *
   * @param incubatorId incubator ID.
   * @return live list of batches for the specified incubator.
   */
  LiveData<List<Batch>> getByIncubator(long incubatorId);

  /**
   * Returns a batch together with its egg groups.
   *
   * @param id batch ID.
   * @return live batch-with-groups view for the specified batch.
   */
  LiveData<BatchWithEggGroups> getWithGroups(long id);

  /**
   * Returns all batches together with their egg groups.
   *
   * @return live list of batch-with-groups views.
   */
  LiveData<List<BatchWithEggGroups>> getAllWithGroups();

  /**
   * Returns all batches together with their associated incubators.
   *
   * @return live list of batch-with-incubator views.
   */
  LiveData<List<BatchWithIncubator>> getAllWithIncubator();

  /**
   * Returns all batches with incubator information ordered by expected hatch date.
   *
   * @return live list of batch-with-incubator views ordered by expected hatch date.
   */
  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByExpectedHatch();

  /**
   * Returns all batches with incubator information ordered by incubator.
   *
   * @return live list of batch-with-incubator views ordered by incubator.
   */
  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByIncubator();

  /**
   * Returns all batches with incubator information ordered by batch number.
   *
   * @return live list of batch-with-incubator views ordered by batch number.
   */
  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByBatchNumber();

  /**
   * Inserts or updates a batch.
   *
   * @param batch batch to save.
   * @return future completing with the saved batch ID.
   */
  CompletableFuture<Long> save(Batch batch);

  /**
   * Saves a batch and creates its initial egg-group data for the specified breed.
   *
   * @param batch batch to save.
   * @param breed breed name used to initialize the batch's egg groups.
   * @return future completing with the saved batch ID.
   */
  CompletableFuture<Long> saveWithInitialEggGroups(Batch batch, String breed);

  /**
   * Deletes a batch.
   *
   * @param batch batch to delete.
   * @return future completing when deletion is finished.
   */
  CompletableFuture<Void> delete(Batch batch);
}