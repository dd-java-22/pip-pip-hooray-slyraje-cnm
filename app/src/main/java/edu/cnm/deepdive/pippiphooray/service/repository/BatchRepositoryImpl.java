package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.BatchDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * Repository implementation for batch data access and operations.
 */
public class BatchRepositoryImpl implements BatchRepository {

  private final BatchDao batchDao;
  private final EggGroupRepository eggGroupRepository;
  private final EggRepository eggRepository;

  /**
   * Constructs a BatchRepositoryImpl with the specified repositories and DAO.
   *
   * @param batchDao the data access object for batch operations
   * @param eggGroupRepository the repository for egg group operations
   * @param eggRepository the repository for egg operations
   */
  @Inject
  BatchRepositoryImpl(
      BatchDao batchDao,
      EggGroupRepository eggGroupRepository,
      EggRepository eggRepository
  ) {
    this.batchDao = batchDao;
    this.eggGroupRepository = eggGroupRepository;
    this.eggRepository = eggRepository;
  }

  @Override
  public LiveData<List<Batch>> getAll() {
    return batchDao.selectAll();
  }

  @Override
  public LiveData<Batch> get(long id) {
    return batchDao.select(id);
  }

  @Override
  public LiveData<List<Batch>> getByIncubator(long incubatorId) {
    return batchDao.selectByIncubator(incubatorId);
  }

  @Override
  public LiveData<BatchWithEggGroups> getWithGroups(long id) {
    return batchDao.selectWithGroups(id);
  }

  @Override
  public LiveData<List<BatchWithEggGroups>> getAllWithGroups() {
    return batchDao.selectAllWithGroups();
  }

  @Override
  public LiveData<List<BatchWithIncubator>> getAllWithIncubator() {
    return batchDao.selectAllWithIncubator();
  }

  @Override
  public LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByExpectedHatch() {
    return batchDao.selectAllWithIncubatorOrderByExpectedHatch();
  }

  @Override
  public LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByIncubator() {
    return batchDao.selectAllWithIncubatorOrderByIncubator();
  }

  @Override
  public LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByBatchNumber() {
    return batchDao.selectAllWithIncubatorOrderByBatchNumber();
  }

  @Override
  public CompletableFuture<Long> save(Batch batch) {
    return CompletableFuture.supplyAsync(() -> {
      if (batch.getId() == 0L) {
        long id = batchDao.insert(batch);
        batch.setId(id);
        return id;
      } else {
        batchDao.update(batch);
        return batch.getId();
      }
    });
  }

  @Override
  public CompletableFuture<Long> saveWithInitialEggGroups(Batch batch, String breed) {
    return save(batch)
        .thenCompose(batchId -> {
          List<EggGroup> groups = buildInitialEggGroups(batchId, breed, batch.getNumEggsSet());
          return eggGroupRepository.saveAll(groups)
              .thenCompose(ids -> seedEggsForGroups(groups))
              .thenApply(ignored -> batchId);
        });
  }

  @Override
  public CompletableFuture<Void> delete(Batch batch) {
    return CompletableFuture.runAsync(() -> {
      batchDao.delete(batch);
    });
  }

  /**
   * Builds initial egg groups for a batch.
   *
   * @param batchId the ID of the batch
   * @param breed the breed for the egg group
   * @param eggCount the number of eggs in the group
   * @return a list containing a single egg group with the specified parameters
   */
  private List<EggGroup> buildInitialEggGroups(long batchId, String breed, int eggCount) {
    List<EggGroup> groups = new ArrayList<>();

    EggGroup group = new EggGroup();
    group.setBatchId(batchId);
    group.setBreed(breed);
    group.setInitialEggCount(eggCount);
    group.setNotes(null);

    groups.add(group);
    return groups;
  }

  /**
   * Seeds eggs for all groups in the provided list.
   *
   * @param groups the list of egg groups to seed
   * @return a CompletableFuture that completes when all eggs have been seeded
   */
  private CompletableFuture<Void> seedEggsForGroups(List<EggGroup> groups) {
    CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);

    for (EggGroup group : groups) {
      chain = chain.thenCompose(ignored ->
          eggRepository.seedEggsForGroup(group.getId(), group.getInitialEggCount())
      );
    }

    return chain;
  }
}