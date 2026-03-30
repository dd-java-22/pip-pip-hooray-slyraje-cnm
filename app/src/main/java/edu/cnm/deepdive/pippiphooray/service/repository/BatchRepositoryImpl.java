package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.BatchDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

public class BatchRepositoryImpl implements BatchRepository {

  private final BatchDao batchDao;

  @Inject
  BatchRepositoryImpl(BatchDao batchDao) {
    this.batchDao = batchDao;
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
  public CompletableFuture<Void> delete(Batch batch) {
    return CompletableFuture.runAsync(() -> {
      batchDao.delete(batch);
    });
  }
}