package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.IncubatorDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * Repository implementation for incubator data access and operations.
 */
public class IncubatorRepositoryImpl implements IncubatorRepository {

  private final IncubatorDao incubatorDao;

  /**
   * Constructs an IncubatorRepositoryImpl with the specified DAO.
   *
   * @param incubatorDao the data access object for incubator operations
   */
  @Inject
  IncubatorRepositoryImpl(IncubatorDao incubatorDao) {
    this.incubatorDao = incubatorDao;
  }

  @Override
  public LiveData<List<Incubator>> getActiveIncubators() {
    return incubatorDao.selectAll();
  }

  @Override
  public LiveData<Incubator> get(long id) {
    return incubatorDao.select(id);
  }

  @Override
  public CompletableFuture<Long> save(Incubator incubator) {
    return CompletableFuture.supplyAsync(() -> {
      if (incubator.getId() == 0L) {
        long id = incubatorDao.insert(incubator);
        incubator.setId(id);
        return id;
      } else {
        incubatorDao.update(incubator);
        return incubator.getId();
      }
    });
  }

  @Override
  public CompletableFuture<Void> deactivate(Incubator incubator) {
    return CompletableFuture.runAsync(() -> {
      incubator.setActive(false);
      incubatorDao.update(incubator);
    });
  }
}