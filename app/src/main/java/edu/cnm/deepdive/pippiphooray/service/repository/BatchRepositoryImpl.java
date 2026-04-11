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

public class BatchRepositoryImpl implements BatchRepository {

  private final BatchDao batchDao;
  private final EggGroupRepository eggGroupRepository;

  @Inject
  BatchRepositoryImpl(BatchDao batchDao, EggGroupRepository eggGroupRepository) {
    this.batchDao = batchDao;
    this.eggGroupRepository = eggGroupRepository;
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
        .thenCompose(batchId -> eggGroupRepository
            .saveAll(buildInitialEggGroups(batchId, breed, batch.getNumEggsSet()))
            .thenApply(ids -> batchId));
  }

  @Override
  public CompletableFuture<Void> delete(Batch batch) {
    return CompletableFuture.runAsync(() -> {
      batchDao.delete(batch);
    });
  }

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
}