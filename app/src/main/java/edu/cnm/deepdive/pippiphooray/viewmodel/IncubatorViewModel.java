package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import edu.cnm.deepdive.pippiphooray.service.repository.IncubatorRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * ViewModel for managing incubator data and operations.
 */
@HiltViewModel
public class IncubatorViewModel extends ViewModel {

  private final IncubatorRepository incubatorRepository;
  private final LiveData<List<Incubator>> activeIncubators;

  /**
   * Constructs an IncubatorViewModel with the specified repository.
   *
   * @param incubatorRepository the repository for incubator data access
   */
  @Inject
  IncubatorViewModel(IncubatorRepository incubatorRepository) {
    this.incubatorRepository = incubatorRepository;
    this.activeIncubators = incubatorRepository.getActiveIncubators();
  }

  /**
   * Gets the list of all active incubators as LiveData.
   *
   * @return a LiveData list of active incubators
   */
  public LiveData<List<Incubator>> getActiveIncubators() {
    return activeIncubators;
  }

  /**
   * Saves an incubator asynchronously.
   *
   * @param incubator the incubator to save
   * @return a CompletableFuture containing the ID of the saved incubator
   */
  public CompletableFuture<Long> save(Incubator incubator) {
    return incubatorRepository.save(incubator);
  }

  /**
   * Deactivates an incubator asynchronously.
   *
   * @param incubator the incubator to deactivate
   * @return a CompletableFuture that completes when the operation finishes
   */
  public CompletableFuture<Void> deactivate(Incubator incubator) {
    return incubatorRepository.deactivate(incubator);
  }

}