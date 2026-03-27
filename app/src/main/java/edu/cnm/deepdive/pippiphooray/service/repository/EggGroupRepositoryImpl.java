package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.EggGroupDao;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

public class EggGroupRepositoryImpl implements EggGroupRepository {

  private final EggGroupDao eggGroupDao;

  @Inject
  EggGroupRepositoryImpl(EggGroupDao eggGroupDao) {
    this.eggGroupDao = eggGroupDao;
  }

  @Override
  public LiveData<List<EggGroup>> getByBatch(long batchId) {
    return eggGroupDao.selectByBatch(batchId);
  }

  @Override
  public LiveData<EggGroup> get(long id) {
    return eggGroupDao.select(id);
  }

  @Override
  public CompletableFuture<Long> save(EggGroup eggGroup) {
    return CompletableFuture.supplyAsync(() -> {
      if (eggGroup.getId() == 0L) {
        long id = eggGroupDao.insert(eggGroup);
        eggGroup.setId(id);
        return id;
      } else {
        eggGroupDao.update(eggGroup);
        return eggGroup.getId();
      }
    });
  }

}