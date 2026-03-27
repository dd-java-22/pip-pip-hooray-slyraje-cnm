package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.dao.CandlingObservationDao;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

public class CandlingObservationRepositoryImpl implements CandlingObservationRepository {

  private final CandlingObservationDao observationDao;

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