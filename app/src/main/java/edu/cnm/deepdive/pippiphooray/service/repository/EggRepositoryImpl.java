package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.EggDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

public class EggRepositoryImpl implements EggRepository {

  private final EggDao eggDao;

  @Inject
  EggRepositoryImpl(EggDao eggDao) {
    this.eggDao = eggDao;
  }

  @Override
  public LiveData<List<Egg>> getByEggGroupId(long eggGroupId) {
    return eggDao.selectByEggGroup(eggGroupId);
  }

  @Override
  public LiveData<Egg> get(long id) {
    return eggDao.select(id);
  }

  @Override
  public CompletableFuture<Long> save(Egg egg) {
    return CompletableFuture.supplyAsync(() -> {
      if (egg.getId() == 0L) {
        long id = eggDao.insert(egg);
        egg.setId(id);
        return id;
      } else {
        eggDao.update(egg);
        return egg.getId();
      }
    });
  }
}