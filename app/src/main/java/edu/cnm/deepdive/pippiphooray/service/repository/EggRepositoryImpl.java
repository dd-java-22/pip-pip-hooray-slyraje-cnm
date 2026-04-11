package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.EggDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import java.util.ArrayList;
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
  public CompletableFuture<List<Egg>> fetchByEggGroupId(long eggGroupId) {
    return CompletableFuture.supplyAsync(() -> eggDao.fetchByEggGroup(eggGroupId));
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

  @Override
  public CompletableFuture<Void> saveAll(List<Egg> eggs) {
    return CompletableFuture.runAsync(() -> {
      if (eggs == null || eggs.isEmpty()) {
        return;
      }

      boolean allNew = true;
      for (Egg egg : eggs) {
        if (egg.getId() != 0L) {
          allNew = false;
          break;
        }
      }

      if (allNew) {
        List<Long> ids = eggDao.insertAll(eggs);
        for (int i = 0; i < eggs.size() && i < ids.size(); i++) {
          Long id = ids.get(i);
          if (id != null) {
            eggs.get(i).setId(id);
          }
        }
      } else {
        eggDao.update(eggs);
      }
    });
  }

  @Override
  public CompletableFuture<Void> seedEggsForGroup(long eggGroupId, int initialEggCount) {
    return CompletableFuture.runAsync(() -> {
      if (initialEggCount <= 0) {
        return;
      }

      List<Egg> eggs = new ArrayList<>();
      for (int eggNumber = 1; eggNumber <= initialEggCount; eggNumber++) {
        Egg egg = new Egg();
        egg.setEggGroupId(eggGroupId);
        egg.setEggNumber(eggNumber);
        egg.setHatchStatus(null);
        egg.setFinalNotes(null);
        eggs.add(egg);
      }

      List<Long> ids = eggDao.insertAll(eggs);
      for (int i = 0; i < eggs.size() && i < ids.size(); i++) {
        Long id = ids.get(i);
        if (id != null) {
          eggs.get(i).setId(id);
        }
      }
    });
  }
}