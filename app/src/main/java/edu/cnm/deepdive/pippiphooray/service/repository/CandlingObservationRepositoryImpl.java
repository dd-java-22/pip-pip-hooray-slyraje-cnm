package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.CandlingObservationDao;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * Repository implementation for candling observation data access and operations.
 */
public class CandlingObservationRepositoryImpl implements CandlingObservationRepository {

  private final CandlingObservationDao observationDao;

  /**
   * Constructs a CandlingObservationRepositoryImpl with the specified DAO.
   *
   * @param observationDao the data access object for candling observation operations
   */
  @Inject
  CandlingObservationRepositoryImpl(CandlingObservationDao observationDao) {
    this.observationDao = observationDao;
  }

  @Override
  public LiveData<List<CandlingObservation>> getByEgg(long eggId) {
    return observationDao.selectByEgg(eggId);
  }

  @Override
  public LiveData<CandlingObservation> get(long id) {
    return observationDao.select(id);
  }

  @Override
  public CompletableFuture<Long> save(CandlingObservation observation) {
    return CompletableFuture.supplyAsync(() -> {
      if (observation.getId() == 0L) {
        observation.setTimestamp(Instant.now());
        long id = observationDao.insert(observation);
        observation.setId(id);
        return id;
      } else {
        return observation.getId();
      }
    });
  }
}