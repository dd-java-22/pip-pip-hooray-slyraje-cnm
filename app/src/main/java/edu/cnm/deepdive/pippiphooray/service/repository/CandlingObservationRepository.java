package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Abstraction for accessing and modifying {@link CandlingObservation} entities.
 */
public interface CandlingObservationRepository {

  /**
   * Returns all candling observations recorded for a given egg.
   *
   * @param eggId egg ID.
   * @return live list of observations for the specified egg.
   */
  LiveData<List<CandlingObservation>> getByEgg(long eggId);

  /**
   * Returns a single candling observation by ID.
   *
   * @param id observation ID.
   * @return live observation with the specified ID.
   */
  LiveData<CandlingObservation> get(long id);

  /**
   * Inserts or updates a candling observation.
   *
   * @param observation observation to save.
   * @return future completing with the saved observation ID.
   */
  CompletableFuture<Long> save(CandlingObservation observation);
}