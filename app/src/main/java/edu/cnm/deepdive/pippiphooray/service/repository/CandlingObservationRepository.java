package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CandlingObservationRepository {

  LiveData<List<CandlingObservation>> getByEgg(long eggId);

  LiveData<CandlingObservation> get(long id);

  CompletableFuture<Long> save(CandlingObservation observation);
}